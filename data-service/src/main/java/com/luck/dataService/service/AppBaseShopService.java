package com.luck.dataService.service;

import com.alibaba.fastjson.JSONObject;
import org.beetl.sql.core.engine.PageQuery;

public interface AppBaseShopService {
    //商铺列表
    PageQuery<JSONObject> getShopList(Integer pageNum, Integer pageSize,String shopName,String shopType);

}
