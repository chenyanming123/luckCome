package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.*;
import com.luck.dataEntity.*;
import com.luck.dataService.common.AreaName;
import com.luck.dataService.service.AppDataImagesService;
import com.luck.dataService.service.AppDataUserinfoService;
import com.luck.dataService.utils.MassageUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppDataUserinfoServiceImpl implements AppDataUserinfoService {
    @Autowired
    AppDataUserinfoDao appDataUserinfoDao;
    @Autowired
    AppDataImagesDao appDataImagesDao;
    @Autowired
    AppDataAppointmentDao appDataAppointmentDao;
    @Autowired
    AppUserDao appUserDao;
    @Autowired
    AppDataImagesService appDataImagesService;
    @Autowired
    AppDataPayDao appDataPayDao;

    @Override
    @Transactional
    public Map addOrUpdataUserInfo(AppDataUserinfoUtil appDataUserinfoUtil) {
        AppDataUserinfo appDataUserinfo = appDataUserinfoUtil.getAppDataUserinfo();
        try{
            AppDataUserinfo appDataUserinfo1 = appDataUserinfoDao.unique(appDataUserinfo.getUserId());
            //删除文件
            appDataImagesService.deleteImages(appDataUserinfoUtil.getDeleteFileIds());
            return updataUserInfo(appDataUserinfo);
        }catch (Exception e){
            return addUserInfo(appDataUserinfo);
        }
    }

    @Override
    public Map addUserInfo(AppDataUserinfo appDataUserinfo) {
        Map map = new HashMap();
        try {
            //统一处理行政区，将字节的河北-石家庄市 转换为 河北省-石家庄市
            appDataUserinfo.setAddress(AreaName.getNewAreaName(appDataUserinfo.getAddress()));
            appDataUserinfo.setDomicile(AreaName.getNewAreaName(appDataUserinfo.getDomicile()));

            appDataUserinfo.setCreateTime(new Date());
            appDataUserinfo.setRegisterTime(new Date());
            appDataUserinfoDao.insert(appDataUserinfo);
            return MassageUtils.getMsg("200","添加成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","添加失败");
        }
    }
    @Override
    public Map updataUserInfo(AppDataUserinfo appDataUserinfo) {
        Map map = new HashMap();
        try {
            //统一处理行政区，将字节的河北-石家庄市 转换为 河北省-石家庄市
            appDataUserinfo.setAddress(AreaName.getNewAreaName(appDataUserinfo.getAddress()));
            appDataUserinfo.setDomicile(AreaName.getNewAreaName(appDataUserinfo.getDomicile()));
            appDataUserinfo.setCreateTime(new Date());
            appDataUserinfo.setRegisterTime(new Date());
            appDataUserinfoDao.updateById(appDataUserinfo);
            return MassageUtils.getMsg("200","修改成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","修改失败");
        }
    }
    @Override
    public JSONObject getUserInfo(Integer queryUserId,Integer userId) {
        JSONObject jsonObject = new JSONObject();
        if(queryUserId != null && !"".equals(queryUserId)){
            Map userinfoMap = appDataUserinfoDao.getUserinfo(queryUserId);
            jsonObject.put("appDataUserinfo",userinfoMap);
        }
        //查询用户上传的图片信息
        List<JSONObject> appDataImagesList = appDataImagesDao.getImagesByUserId(queryUserId);
        jsonObject.put("appDataImages",appDataImagesList);
        //返回数据操作状态
        if(userId != null && !"".equals(userId)){
            Map map = getOperationStatus(queryUserId,userId);
            jsonObject.put("operationStatus",map.get("operationStatus"));
            jsonObject.put("appointmentId",map.get("appointmentId"));
        }else{
            jsonObject.put("operationStatus",null);
            jsonObject.put("appointmentId",null);
        }
        return jsonObject;
    }
    @Override
    public JSONObject getMyUserInfo(Integer userId) {
        JSONObject jsonObject = new JSONObject();
        Map userinfoMap = appDataUserinfoDao.getMyUserinfo(userId);
        jsonObject.put("appDataUserinfo",userinfoMap);
        //查询用户上传的图片信息
        List<JSONObject> appDataImagesList = appDataImagesDao.getImagesByUserId(userId);
        jsonObject.put("appDataImages",appDataImagesList);
        return jsonObject;
    }

    //列表页查看详情时，返回数据操作状态
    // 1.相互不喜欢-发起约会，
    // 2.我喜欢的-取消邀约
    // 3.喜欢我的-去约会(选择最终时间地点  并支付)
    // 4.互相喜欢-我已付款(等待对方付款)
    // 5.互相喜欢-对方已付款(直接付款)
    // 6.双方付款-到订单页  (进行中)
    public Map getOperationStatus(Integer queryUserId,Integer userId){
        Map map = new HashMap();
        map.put("operationStatus",1);
//        Integer operationStatus = 1;
        //我发起的
        //0：我喜欢或喜欢我，1：互相喜欢，2：取消，3：拒绝
        AppDataAppointment appDataAppointment = appDataAppointmentDao.queryOnlyOneByUserIdAndOtherId(userId,queryUserId);
        if (appDataAppointment != null) {
            map.put("appointmentId",appDataAppointment.getId());//返回约会id
            if(appDataAppointment.getStatus() == 0){
                map.put("operationStatus",2);
            }else if(appDataAppointment.getStatus() == 1){
                //查询付款状态
                map.put("operationStatus",getPayStatus(appDataAppointment.getId(),queryUserId,userId));
            }
        }
        //对方发起的
        AppDataAppointment appDataAppointment2 = appDataAppointmentDao.queryOnlyOneByUserIdAndOtherId(queryUserId,userId);
        if (appDataAppointment2 != null) {
            map.put("appointmentId",appDataAppointment2.getId());//返回约会id
            if(appDataAppointment2.getStatus() == 0){
                map.put("operationStatus",3);
            }else if(appDataAppointment2.getStatus() == 1){
                //查询付款状态
                map.put("operationStatus",getPayStatus(appDataAppointment2.getId(),queryUserId,userId));
            }
        }
        return map;
    }
    // 4.互相喜欢-我已付款(等待对方付款)
    // 5.互相喜欢-对方已付款(直接付款)
    // 6.双方付款-到订单页  (进行中)
    //获取付款状态
    public Integer getPayStatus(Integer appDataAppointmentId,Integer queryUserId,Integer userId){
        Integer operationStatus;
        AppDataPay appDataPayMe = appDataPayDao.createLambdaQuery().andEq(AppDataPay::getAppointmentId,appDataAppointmentId).andEq(AppDataPay::getUserId,userId).single();
        AppDataPay appDataPayOther = appDataPayDao.createLambdaQuery().andEq(AppDataPay::getAppointmentId,appDataAppointmentId).andEq(AppDataPay::getUserId,queryUserId).single();
        if (appDataPayMe != null && appDataPayOther != null){
            operationStatus = 6;//双方已付款
        }else if(appDataPayMe != null && appDataPayOther == null){
            operationStatus = 4;;//我已付款(等待对方付款)
        }else{
            operationStatus = 5;//对方已付款(直接付款)
        }
        return operationStatus;
    }

    @Override
    @Transactional
    public PageQuery<JSONObject> queryUserInfo(Integer pageNum, Integer pageSize,Integer userId, String address,Integer age_start,Integer age_end) {
        //查询当前用户信息
        Integer sex = 1;
        if(userId != null){
            try {
                //未添加资料
                AppDataUserinfo appDataUserinfo = appDataUserinfoDao.unique(userId);
                if(appDataUserinfo.getSex() == 1){
                    sex = 2;
                }
            }catch (Exception e){
                //以用户数据未准
                AppUser appUser = appUserDao.unique(userId);
                if(appUser.getGender() == 1){
                    sex = 2;
                }
            }
        }
        Map<String, Object> params = new HashMap<>();
        if(userId != null){//未登录
            params.put("sex",sex);
        }
        //统一处理行政区，将字节的河北-石家庄市 转换为 河北省-石家庄市
        params.put("address",AreaName.getNewAreaName(address));
        params.put("age_start",age_start);
        params.put("age_end",age_end);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appDataUserinfoDao.getSQLManager().pageQuery("appDataUserinfo.queryUserInfo", JSONObject.class, query);
        return query;
    }


}
