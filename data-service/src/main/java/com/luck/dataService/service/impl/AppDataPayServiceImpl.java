package com.luck.dataService.service.impl;

import com.luck.dataDao.AppDataPayDao;
import com.luck.dataEntity.AppDataPay;
import com.luck.dataEntity.AppDataUserinfo;
import com.luck.dataService.service.AppDataPayService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Map;

@Service
public class AppDataPayServiceImpl implements AppDataPayService {
    @Autowired
    private AppDataPayDao appDataPayDao;


    @Transactional
    @Override
    public Integer addPayInfo(AppDataPay appDataPay) {
//        appDataPay.setPayTime(new Date());
        appDataPayDao.insert(appDataPay,true);
        return appDataPay.getId();
    }
}
