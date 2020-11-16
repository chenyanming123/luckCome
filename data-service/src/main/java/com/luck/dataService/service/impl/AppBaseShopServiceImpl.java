package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.AppBaseShopDao;
import com.luck.dataService.service.AppBaseShopService;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AppBaseShopServiceImpl implements AppBaseShopService {
    @Autowired
    AppBaseShopDao appBaseShopDao;

    @Override
    public PageQuery<JSONObject> getShopList(Integer pageNum, Integer pageSize, String shopName, String shopType) {
        Map<String, Object> params = new HashMap<>();
        params.put("shopName",shopName);
        params.put("shopType",shopType);
        PageQuery query = new PageQuery(pageNum, pageSize, params);
        appBaseShopDao.getSQLManager().pageQuery("appBaseShop.getShopList", JSONObject.class, query);
        return query;
    }
}
