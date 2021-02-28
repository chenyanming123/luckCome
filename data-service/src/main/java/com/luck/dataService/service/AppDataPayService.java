package com.luck.dataService.service;

import com.luck.dataEntity.AppDataPay;

import java.util.Map;

public interface AppDataPayService {
    //添加用户支付信息
    Map addPayInfo(Integer appointmentId,Integer userId,Integer payMoney);
}
