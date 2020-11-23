package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.TokenApp;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.manager.TokenManagerApp;
import com.luck.dataService.service.StorageSyncService;
import com.luck.dataService.utils.AesCbcUtil;
import com.luck.dataService.utils.HttpClientUtil;
import com.luck.dataService.utils.MassageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StorageSyncServiceImpl implements StorageSyncService {
    @Value("${token.persistSeconds:3600}")
    private long tokenPersistSecond;
    @Autowired
    private RedisTemplate<String, String> redisTemplateByToken;
    @Autowired
    private TokenManagerApp tokenManagerApp;
    @Value("${token.expiresSeconds:86400}")
    private long tokenExpiresSecond;
    @Override
    public Map getCode2Session(JSONObject code) throws ApiException {
        Map map = new HashMap();
        Map param = new HashMap();
        String url = "";
        if(!code.isEmpty()){
            if(code.get("appType").equals("WX")){
//                param.put("appid", "wx14cc6d8a1ab36dc1");
//                param.put("secret", "e48059e7a14f1591fa656c10ea97193e");
                param.put("appid", "wx2ad0c11e3bd4a1a4");
                param.put("secret", "54a39f4ee7ed820833cd577ad90511d4");
                param.put("js_code", code.get("code"));
                param.put("grant_type", "authorization_code");
                url = "https://api.weixin.qq.com/sns/jscode2session";
            }else if(code.get("appType").equals("DY")){
                param.put("appid", "tta0a1d61c3f9ba9f9");
                param.put("secret", "828da683769296df923b6715acff51fcb29834bf");
                param.put("code", code.get("code"));
                url = "https://developer.toutiao.com/api/apps/jscode2session";
            }
            //利用code换取openid和session_key
            JSONObject openid_session_key = JSONObject.parseObject(HttpClientUtil.httpGetWithJSON(url,param));
            if(!"".equals(openid_session_key.get("openid")) && !"".equals(openid_session_key.get("session_key"))){
//                String uuid = UUID.randomUUID().toString().replace("-","");
                TokenApp tokenApp = new TokenApp();
                tokenApp.setOpenId(openid_session_key.get("openid").toString());
                tokenApp.setSessionkey(openid_session_key.get("session_key").toString());
                System.out.println(openid_session_key.get("session_key").toString());
                //生成token，并保存到redis
                String token = tokenManagerApp.generateToken(tokenApp);
                map = MassageUtils.getMsg("200","请求成功！");
                map.put("token",token);
            }else{
                //code无效
                map = MassageUtils.getMsg("500","code无效！");
            }
        }else{
            //code为空
            map = MassageUtils.getMsg("500","code为空！");
        }
        return map;
    }

    @Override
    public String getUserInfo(String encryptedData,String session_key,String iv) {
        Map map = new HashMap();
        try {
            String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
            if (null != result && result.length() > 0) {
                map.put("status", 1);
                map.put("msg", "解密成功");

//                JSONObject userInfoJSON = new JSONObject(result);
                JSONObject userInfoJSON = JSONObject.parseObject(result);
                Map userInfo = new HashMap();
                userInfo.put("openId", userInfoJSON.get("openId"));
                userInfo.put("nickName", userInfoJSON.get("nickName"));
                userInfo.put("gender", userInfoJSON.get("gender"));
                userInfo.put("city", userInfoJSON.get("city"));
                userInfo.put("province", userInfoJSON.get("province"));
                userInfo.put("country", userInfoJSON.get("country"));
                userInfo.put("avatarUrl", userInfoJSON.get("avatarUrl"));
                // 解密unionId & openId;

                userInfo.put("unionId", userInfoJSON.get("unionId"));
                map.put("userInfo", userInfo);
            } else {
                map.put("status", 0);
                map.put("msg", "解密失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
