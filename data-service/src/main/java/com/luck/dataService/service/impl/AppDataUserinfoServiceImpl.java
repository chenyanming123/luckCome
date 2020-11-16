package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.AppDataAppointmentDao;
import com.luck.dataDao.AppDataImagesDao;
import com.luck.dataDao.AppDataUserinfoDao;
import com.luck.dataDao.AppUserDao;
import com.luck.dataEntity.AppDataAppointment;
import com.luck.dataEntity.AppDataUserinfo;
import com.luck.dataEntity.AppDataUserinfoUtil;
import com.luck.dataEntity.AppUser;
import com.luck.dataService.common.AreaName;
import com.luck.dataService.service.AppDataImagesService;
import com.luck.dataService.service.AppDataUserinfoService;
import com.luck.dataService.utils.MassageUtils;
import io.swagger.annotations.ApiModelProperty;
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
            Integer operationStatus = getOperationStatus(queryUserId,userId);
            jsonObject.put("operationStatus",operationStatus);
        }else{
            jsonObject.put("operationStatus",null);
        }
        return jsonObject;
    }
    //列表页查看详情时，返回数据操作状态
    // 1.相互不喜欢-发起约会，
    // 2.我喜欢的-取消邀约
    // 3.喜欢我的-去约会(选择最终时间地点  并支付)
    // 4.互相喜欢-我已付款(等待对方付款)
    // 5.互相喜欢-对方已付款(直接付款)
    // 6.双方付款-到订单页  (进行中)
    public Integer getOperationStatus(Integer queryUserId,Integer userId){
        Integer operationStatus = 1;
        //我发起的
        //0：我喜欢或喜欢我，1：互相喜欢，2：取消，3：拒绝
        AppDataAppointment appDataAppointment = appDataAppointmentDao.queryOnlyOneByUserIdAndOtherId(userId,queryUserId);
        if (appDataAppointment != null) {
            if(appDataAppointment.getStatus() == 0){
                operationStatus = 2;
            }else if(appDataAppointment.getStatus() == 1){
                operationStatus = 4;//待处理付款状态
            }
        }
        //对方发起的
        AppDataAppointment appDataAppointment2 = appDataAppointmentDao.queryOnlyOneByUserIdAndOtherId(queryUserId,userId);
        if (appDataAppointment != null) {
            if(appDataAppointment2.getStatus() == 0){
                operationStatus = 3;
            }else if(appDataAppointment2.getStatus() == 1){
                operationStatus = 4;//待处理付款状态
            }
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
