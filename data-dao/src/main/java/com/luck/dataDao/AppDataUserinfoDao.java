package com.luck.dataDao;

import com.luck.dataEntity.AppDataUserinfo;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.Map;

public interface AppDataUserinfoDao extends BaseMapper<AppDataUserinfo> {
    //根据userId查询用户资料信息
    Map getUserinfo(@Param("userId") Integer userId);
}
