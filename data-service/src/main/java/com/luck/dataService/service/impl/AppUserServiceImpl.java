package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.AppUserDao;
import com.luck.dataEntity.AppUser;
import com.luck.dataService.service.AppUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AppUserServiceImpl implements AppUserService {
    @Autowired
    AppUserDao appUserDao;
    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Override
    public Integer addOrUpdataUserInfo(JSONObject userInfo) {
        AppUser appUser = new AppUser();
        if(!userInfo.isEmpty()){
            appUser.setNickName(userInfo.get("nickName").toString());
            appUser.setMobile(userInfo.get("mobile").toString());
            appUser.setCountry(userInfo.get("country").toString());
            appUser.setProvince(userInfo.get("province").toString());
            appUser.setCity(userInfo.get("city").toString());
            appUser.setLanguage(userInfo.get("language").toString());
            if("DY".equals(userInfo.get("appType"))){
                //抖音-用户信息中性别，男 0 女 1
                if("0".equals(userInfo.get("gender").toString())){
                    appUser.setGender(1);
                }else {
                    appUser.setGender(2);
                }
                //抖音头像路径
                appUser.setAvatarUrlDy(userInfo.get("avatarUrl").toString());
            }else if("WX".equals(userInfo.get("appType"))){
                //微信-用户信息中性别，0：未知、1：男、2：女
                appUser.setGender(Integer.parseInt(userInfo.get("gender").toString()));
                //微信头像路径
                appUser.setAvatarUrlWx(userInfo.get("avatarUrl").toString());
            }
            appUser.setRegisterTime(new Date());
            appUser.setCreateTime(new Date());
            //判断用户是否存在
//            AppUser appUserOld = appUserDao.createLambdaQuery().orEq(AppUser::getMobile, userInfo.get("mobile").toString()).unique();
            AppUser appUserOld = getUserInfoByMobile(userInfo.get("mobile").toString());
            if(appUserOld != null){
                //更新用户信息
                appUser.setId(appUserOld.getId());
                appUserDao.updateById(appUser);
            }else {
                //添加用户信息
                appUserDao.insert(appUser,true);
            }
        }
        return appUser.getId();
    }

    @Override
    public AppUser getUserInfo(JSONObject userInfo) {
        return appUserDao.unique(addOrUpdataUserInfo(userInfo));
    }

    @Override
    public AppUser getUserInfoByMobile(String mobile) {
        AppUser appUser = null;
        if (StringUtils.isEmpty(mobile))
            return appUser;
        appUser = appUserDao.getAppUserBymobile(mobile);
        return appUser;
    }
}
