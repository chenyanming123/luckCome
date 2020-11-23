package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.AppBaseShopDao;
import com.luck.dataDao.AppDataAppointmentDao;
import com.luck.dataEntity.AppBaseShop;
import com.luck.dataEntity.AppDataAppointment;
import com.luck.dataService.service.AppDataAppointmentService;
import com.luck.dataService.service.AppDataUserinfoService;
import com.luck.dataService.utils.MassageUtils;
import io.swagger.models.auth.In;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppDataAppointmentServiceImpl implements AppDataAppointmentService {
    //0：我喜欢或喜欢我，1：互相喜欢，2：取消，3：拒绝
    @Autowired
    AppDataAppointmentDao appDataAppointmentDao;
    @Autowired
    AppBaseShopDao appBaseShopDao;
    @Autowired
    AppDataUserinfoService appDataUserinfoService;
    @Override
    public Map addAppDataAppointment(AppDataAppointment appDataAppointment) {
        try {
            appDataAppointment.setCreateTime(new Date());
            appDataAppointment.setRegisterTime(new Date());
            appDataAppointment.setStatus(0);
            appDataAppointment.setDataStatus(1);
            //判断是否已经发起或者被发起
            if(getDataExists(appDataAppointment.getUserId(),appDataAppointment.getOtherId()) == true){
                return MassageUtils.getMsg("500","您已经发起邀约，请勿重复发起！");
            }else if(getDataExists(appDataAppointment.getOtherId(),appDataAppointment.getUserId()) == true){
                return MassageUtils.getMsg("500","对方已邀约您，请在喜欢我的列表中查看！");
            }else{
                appDataAppointmentDao.insert(appDataAppointment);
            }
            return MassageUtils.getMsg("200","添加成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","添加失败");
        }
    }

    @Override
    public Map queryAppDataAppointmentInfo(Integer id,Integer userId) {
        Map map = new HashMap();
        map = appDataAppointmentDao.getAppDataAppointment(id);
        JSONObject placeJson = new JSONObject();
        //获取约会的店铺信息
        List<Map> appBaseShopList = new ArrayList<>();
        if(map.get("placeId") != null && !"".equals(map.get("placeId"))){
            String placeIds[] = map.get("placeId").toString().split(",");
            for(int i=0;i<placeIds.length;i++){
                Map appBaseShopMap = appBaseShopDao.getAppBaseShop(Integer.parseInt(placeIds[i]));
                appBaseShopList.add(appBaseShopMap);
            }
            map.put("appBaseShopList",appBaseShopList);
        }
        JSONObject userInfoJson = new JSONObject();
        if(map.get("userId") == userId){
            userInfoJson = appDataUserinfoService.getUserInfo(Integer.parseInt(map.get("otherId").toString()),userId);
        }else if(map.get("otherId") == userId){
            userInfoJson = appDataUserinfoService.getUserInfo(Integer.parseInt(map.get("userId").toString()),userId);
        }
        map.put("userInfoJson",userInfoJson);
        return map;
    }

    @Override
    public Map updateAppDataAppointment(Integer id, String appointmentTime, String placeId) {
        AppDataAppointment appDataAppointment = appDataAppointmentDao.unique(id);
        appDataAppointment.setAppointmentTime(appointmentTime);
        appDataAppointment.setPlaceId(placeId);
        if(appDataAppointment.getStatus() == 2 || appDataAppointment.getDataStatus() == 0){
            return MassageUtils.getMsg("500","操作失败，对方已取消约会！");
        }
        appDataAppointmentDao.updateById(appDataAppointment);
        return MassageUtils.getMsg("200","操作成功！");
    }

    public boolean getDataExists(Integer userId,Integer otherId) {
        AppDataAppointment appDataAppointment = appDataAppointmentDao.queryOnlyOneByUserIdAndOtherId(userId, otherId);
        if(appDataAppointment != null){
            return true;
        }
        return false;
    }
    @Override
    public Map deleteAppDataAppointment(Integer id) {
        try {
            AppDataAppointment appDataAppointment = new AppDataAppointment();
            appDataAppointment.setRegisterTime(new Date());
            appDataAppointment.setDataStatus(0);
            appDataAppointmentDao.getSQLManager().query(AppDataAppointment.class)
                    .andEq("id",id)
                    .updateSelective(appDataAppointment);
            return MassageUtils.getMsg("200","删除成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","删除失败");
        }
    }

    @Override
    public PageQuery<JSONObject> queryMylove(Integer pageNum,Integer pageSize,Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("status",0);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appDataAppointmentDao.getSQLManager().pageQuery("appDataAppointment.queryMylove", JSONObject.class, query);
        return query;
    }

    @Override
    public PageQuery<JSONObject> queryLoveme(Integer pageNum,Integer pageSize, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("status",0);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appDataAppointmentDao.getSQLManager().pageQuery("appDataAppointment.queryLoveme", JSONObject.class, query);
        return query;
    }

    @Override
    public PageQuery<JSONObject> queryLoveEachOther(Integer pageNum,Integer pageSize, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("status",1);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appDataAppointmentDao.getSQLManager().pageQuery("appDataAppointment.queryLoveEachOther", JSONObject.class, query);
        return query;
    }

    @Override
    public PageQuery<JSONObject> refuseAppointment(Integer pageNum,Integer pageSize, Integer userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId",userId);
        params.put("status",3);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appDataAppointmentDao.getSQLManager().pageQuery("appDataAppointment.queryMylove", JSONObject.class, query);
        return query;
    }

    @Override
    public Map loseInterest(Integer id) {
        try {
            AppDataAppointment appDataAppointment = new AppDataAppointment();
            appDataAppointment.setRegisterTime(new Date());
            appDataAppointment.setStatus(3);
            appDataAppointmentDao.getSQLManager().query(AppDataAppointment.class)
                    .andEq("id",id)
                    .updateSelective(appDataAppointment);
            return MassageUtils.getMsg("200","操作成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","操作失败");
        }
    }

    @Override
    public Map cancelAppointment(Integer id) {
        try {
            AppDataAppointment appDataAppointment = new AppDataAppointment();
            appDataAppointment.setRegisterTime(new Date());
            appDataAppointment.setStatus(2);
            appDataAppointmentDao.getSQLManager().query(AppDataAppointment.class)
                    .andEq("id",id)
                    .updateSelective(appDataAppointment);
            return MassageUtils.getMsg("200","取消成功");
        }catch (Exception e){
            return MassageUtils.getMsg("500","取消失败");
        }
    }
}
