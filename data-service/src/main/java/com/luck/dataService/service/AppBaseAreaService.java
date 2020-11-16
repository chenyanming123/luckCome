package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface AppBaseAreaService {
    //根据行政区划查询下级行政区划，行政区划为空是返回省级行政区划
    List<JSONObject> queryAreaByAreaCode(JSONObject areaCode);
}
