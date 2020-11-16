package com.luck.dataService.manager;


import com.luck.dataEntity.TokenUser;
import com.luck.dataService.exception.ApiException;

/**
 * 对Token进行操作的接口
 */
public interface TokenManager {
    // 生成token
    String generateToken(TokenUser tokenUser) throws ApiException;
    //更新token的生存周期
    void refreshToken(TokenUser tokenUser) throws Exception;
    //根据token获取用户信息
    TokenUser getTokenUserFromToken(String token) throws Exception;
    String getToken(TokenUser tokenUser) throws Exception;
    //清楚token
    void deleteToken(TokenUser tokenUser) throws Exception;
}
