package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataAppointment;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;
import java.util.Map;

public interface AppDataAppointmentService {
    //发起约会
    Map addAppDataAppointment(AppDataAppointment appDataAppointment);
    //查询约会详情,包含约定的时间地点
    Map queryAppDataAppointmentInfo(Integer id,Integer userId);
    //同意邀约并约定唯一的时间、地点
    Map updateAppDataAppointment(Integer userId,Integer id ,String appointmentTime,String placeId);
    //删除约会记录
    Map deleteAppDataAppointment(Integer id);
    //我喜欢的
    PageQuery<JSONObject> queryMylove(Integer pageNum,Integer pageSize, Integer userId);
    //喜欢我的
    PageQuery<JSONObject> queryLoveme(Integer pageNum,Integer pageSize, Integer userId);
    //相互喜欢的
    PageQuery<JSONObject> queryLoveEachOther(Integer pageNum,Integer pageSize, Integer userId);
    //拒绝邀约的
    PageQuery<JSONObject> refuseAppointment(Integer pageNum,Integer pageSize, Integer userId);
    //不感兴趣
    Map loseInterest(Integer id);
    //取消约会
    Map cancelAppointment(Integer id);

}
