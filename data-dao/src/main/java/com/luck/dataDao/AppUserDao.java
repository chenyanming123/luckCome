package com.luck.dataDao;

import com.luck.dataEntity.AppUser;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

public interface AppUserDao extends BaseMapper<AppUser> {
    //通过手机号码获取用户信息——微信
    AppUser getAppUserBymobile_WX(@Param("mobile") String mobile);
    //通过手机号码获取用户信息——微信
    AppUser getAppUserBymobile_DY(@Param("mobile") String mobile);
    //通过手机号码获取用户信息
    AppUser getAppUserBymobile(@Param("mobile") String mobile);
}
