package com.luck.dataDao;

import com.luck.dataEntity.AppDataPay;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

public interface AppDataPayDao extends BaseMapper<AppDataPay> {
    //根据约会id和用户id查询是否付款
    Integer queryPay(@Param("appDataAppointmentId") Integer appDataAppointmentId,@Param("userId") Integer userId);
}
