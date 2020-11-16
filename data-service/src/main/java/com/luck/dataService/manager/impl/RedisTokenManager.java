package com.luck.dataService.manager.impl;

import com.luck.dataEntity.TokenUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.manager.JwtTokenManager;
import com.luck.dataService.manager.TokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储和验证token的实现类
 *
 * @author ScienJus
 * @date 2015/7/31.
 */
@Primary
@Component
public class RedisTokenManager implements TokenManager {

    private String KEY_PREFIX = "login_";
    // token持续有效时间，如果没有api访问，在设置数值秒后失效，优先从spring配置获取
    @Value("${token.persistSeconds:3600}")
    private long tokenPersistSecond;
    // token到期时间，如果一直有api访问，在设置数值秒后失效，优先从spring配置获取
    @Value("${token.expiresSeconds:86400}")
    private long tokenExpiresSecond;

    private RedisTemplate<String, String> redisTemplateByToken;

    @Autowired
    private JwtTokenManager jwtTokenManager;

    @Autowired
    @Qualifier("redisTemplate")
    public void setRedisByToken(RedisTemplate redis) {
        System.out.println("persist " + tokenPersistSecond);
        System.out.println("expires " + tokenExpiresSecond);
        this.redisTemplateByToken = redis;
        this.redisTemplateByToken.setKeySerializer(new StringRedisSerializer());
        this.redisTemplateByToken.setValueSerializer(new StringRedisSerializer());
    }

    @Override
    public String generateToken(TokenUser tokenUser) throws ApiException {
        String token = jwtTokenManager.generateToken(tokenUser, tokenExpiresSecond);
        String key = getKey(tokenUser);
        //存储到redis并设置过期时间
        redisTemplateByToken.boundValueOps(key).set(token, tokenPersistSecond, TimeUnit.SECONDS);
        return token;
    }
    private String getKey(TokenUser tokenUser) throws ApiException {
        String key = KEY_PREFIX;
        if (tokenUser.getUserId() != null) {
//            key = key + tokenUser.getUserId() + "_" + tokenUser.getDevice();
            key = key + tokenUser.getUserId();
        } else {
            throw new ApiException(ErrCode.invalidToken);
        }
        return key;
    }
    @Override
    @Transactional
    public void refreshToken(TokenUser tokenUser) throws ApiException {
        String key = getKey(tokenUser);
        redisTemplateByToken.boundValueOps(key).expire(tokenPersistSecond, TimeUnit.SECONDS);
    }
    @Override
    public TokenUser getTokenUserFromToken(String token) throws Exception {
        TokenUser tokenUser = new TokenUser();
        try {
            tokenUser = jwtTokenManager.getTokenUserFromToken(token);
        } catch (ExpiredJwtException e) {
            throw new ApiException(ErrCode.expireToken);
        } catch (Exception e) {
            throw new ApiException(ErrCode.invalidToken);
        }
        String curToken = getToken(tokenUser);
        if (curToken == null)
            throw new ApiException(ErrCode.invalidToken);
        if (!curToken.equals(token))
            throw new ApiException(ErrCode.beTakenToken);
        return tokenUser;
    }
    @Override
    public String getToken(TokenUser tokenUser) throws ApiException {
        String key = getKey(tokenUser);
        return redisTemplateByToken.boundValueOps(key).get();
    }
    @Override
    public void deleteToken(TokenUser tokenUser) throws ApiException {
        String key = getKey(tokenUser);
        redisTemplateByToken.delete(key);
    }
}
