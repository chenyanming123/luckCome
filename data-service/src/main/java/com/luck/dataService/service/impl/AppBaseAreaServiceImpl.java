package com.luck.dataService.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataDao.AppBaseAreaDao;
import com.luck.dataService.service.AppBaseAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppBaseAreaServiceImpl implements AppBaseAreaService {
    @Autowired
    AppBaseAreaDao appBaseAreaDao;
    @Override
    public List<JSONObject> queryAreaByAreaCode(JSONObject areaCode) {
        String code = "";
        if(!areaCode.isEmpty()){
            code = areaCode.get("areaCode").toString();
        }
        return appBaseAreaDao.queryAreaByAreaCode(code);
    }

}
