package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataUserinfo;
import com.luck.dataEntity.AppDataUserinfoUtil;
import org.beetl.sql.core.engine.PageQuery;

import java.util.Map;

public interface AppDataUserinfoService {
    //添加或者修改
    Map addOrUpdataUserInfo(AppDataUserinfoUtil appDataUserinfoUtil);
    //添加用户资料信息
    Map addUserInfo(AppDataUserinfo appDataUserinfo);
    //修改用户资料信息
    Map updataUserInfo(AppDataUserinfo appDataUserinfo);
    //查看用户资料信息(含上传的图片资料)
    JSONObject getUserInfo(Integer queryUserId,Integer userId);
    //分页列表查看用户资料信息
    PageQuery<JSONObject> queryUserInfo(Integer pageNum,Integer pageSize,Integer userId,String address,Integer age_start,Integer age_end);

}
