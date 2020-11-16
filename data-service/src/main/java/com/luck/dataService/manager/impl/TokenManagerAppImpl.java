package com.luck.dataService.manager.impl;

import com.luck.dataEntity.TokenApp;
import com.luck.dataEntity.TokenUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.manager.JwtTokenManagerApp;
import com.luck.dataService.manager.TokenManagerApp;
import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @author ScienJus
 * @date 2015/7/31.
 */
@Primary
@Component
public class TokenManagerAppImpl implements TokenManagerApp {
    private String KEY_PREFIX = "login_";
    // token持续有效时间，如果没有api访问，在设置数值秒后失效，优先从spring配置获取
    @Value("${token.persistSeconds:3600}")
    private long tokenPersistSecond;
    // token到期时间，如果一直有api访问，在设置数值秒后失效，优先从spring配置获取
    @Value("${token.expiresSeconds:86400}")
    private long tokenExpiresSecond;
    @Autowired
    private RedisTemplate<String, String> redisTemplateByToken;

    @Autowired
    private JwtTokenManagerApp jwtTokenManagerApp;

    @Override
    public String generateToken(TokenApp tokenApp) throws ApiException{
        String token = jwtTokenManagerApp.generateToken(tokenApp, tokenExpiresSecond);
        String key = getKey(tokenApp);
//        String key = UUID.randomUUID().toString().replace("-","");
        //存储到redis并设置过期时间
        redisTemplateByToken.boundValueOps(key).set(token, tokenPersistSecond, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public TokenApp getTokenUserFromToken(String token) throws Exception {
        TokenApp tokenApp = new TokenApp();
        try {
            tokenApp = jwtTokenManagerApp.getTokenUserFromToken(token);
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrCode.expireToken);
        } catch (Exception e) {
            throw new ApiException(ErrCode.invalidToken);
        }
        String curToken = getToken(tokenApp);
        if (curToken == null)
            throw new ApiException(ErrCode.invalidToken);
        if (!curToken.equals(token))
            throw new ApiException(ErrCode.beTakenToken);
        return tokenApp;
    }

    @Override
    public String getToken(TokenApp tokenApp) throws ApiException {
        String key = getKey(tokenApp);
        return redisTemplateByToken.boundValueOps(key).get();
    }
    private String getKey(TokenApp tokenApp) throws ApiException {
        String key = KEY_PREFIX;
        if (tokenApp.getOpenId() != null) {
            key = key + DigestUtils.md5Hex(DigestUtils.md5Hex(tokenApp.getOpenId()));
        } else {
            throw new ApiException(ErrCode.invalidToken);
        }
        return key;
    }
    @Override
    @Transactional
    public void refreshToken(TokenApp tokenApp) throws ApiException {
        String key = getKey(tokenApp);
        redisTemplateByToken.boundValueOps(key).expire(tokenPersistSecond, TimeUnit.SECONDS);
    }
}
