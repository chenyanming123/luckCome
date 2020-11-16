package com.luck.dataService.manager;


import com.luck.dataEntity.TokenApp;
import com.luck.dataService.exception.ApiException;

/**
 * 对Token进行操作的接口
 */
public interface TokenManagerApp {
    // 生成token
    String generateToken(TokenApp tokenApp)throws ApiException;
    //更新token的生存周期
    void refreshToken(TokenApp tokenApp) throws Exception;
    //根据token获取用户信息
    TokenApp getTokenUserFromToken(String token) throws Exception;
    String getToken(TokenApp tokenApp) throws Exception;
//    //清楚token
//    void deleteToken(TokenUser tokenUser) throws Exception;
}
