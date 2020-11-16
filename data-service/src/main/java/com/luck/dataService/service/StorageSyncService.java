package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataService.exception.ApiException;

import java.util.Map;

public interface StorageSyncService {
    //微信/抖音 获取登录态
    Map getCode2Session(JSONObject code)throws ApiException;
    String getUserInfo(String encryptedData,String session_key,String iv);
}
