package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppUser;

public interface AppUserService {
    //将用户信息更新到数据库
    Integer addOrUpdataUserInfo(JSONObject userInfo);
    //将前端获取用户信息新增到数据库
    AppUser getUserInfo(JSONObject userInfo);
    //通过手机号获取用户信息
    AppUser getUserInfoByMobile(String mobile);

}
