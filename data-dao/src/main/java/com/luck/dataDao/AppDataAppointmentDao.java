package com.luck.dataDao;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataAppointment;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface AppDataAppointmentDao extends BaseMapper<AppDataAppointment> {
    //我喜欢的
    List<JSONObject> queryMylove(@Param("userId") Integer userId,@Param("status") Integer status);
    //喜欢我的
    List<JSONObject> queryLoveme(@Param("userId") Integer userId,@Param("status") Integer status);

    AppDataAppointment queryOnlyOneByUserIdAndOtherId(@Param("userId") Integer userId,@Param("otherId") Integer otherId);
    //获取约会记录的部分信息
    Map getAppDataAppointment(@Param("id")Integer id);
}
