package com.luck.dataDao;

import com.luck.dataEntity.AppBaseEducation;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface AppBaseEducationDao extends BaseMapper<AppBaseEducation> {
    //查询学历字典
    List<Map> query();
}
