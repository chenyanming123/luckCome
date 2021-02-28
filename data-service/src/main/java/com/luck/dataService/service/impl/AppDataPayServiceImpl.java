package com.luck.dataService.service.impl;

import com.luck.dataDao.AppDataPayDao;
import com.luck.dataEntity.AppDataPay;
import com.luck.dataService.service.AppDataPayService;
import com.luck.dataService.utils.MassageUtils;
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
    public Map addPayInfo(Integer appointmentId,Integer userId,Integer payMoney) {
        AppDataPay appDataPay = new AppDataPay();
        appDataPay.setUserId(userId);
        appDataPay.setAppointmentId(appointmentId);
        appDataPay.setPayMoney(payMoney);
        appDataPay.setPayTime(new Date());
        appDataPayDao.insert(appDataPay,true);
        return MassageUtils.getMsg("200","支付成功");
    }
}
