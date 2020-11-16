package com.luck.dataDao;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppBaseArea;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface AppBaseAreaDao extends BaseMapper<AppBaseArea> {
    //根据行政区划查询下级行政区划，行政区划为空是返回省级行政区划
    List<JSONObject>  queryAreaByAreaCode(@Param("areaCode") String areaCode);
}
