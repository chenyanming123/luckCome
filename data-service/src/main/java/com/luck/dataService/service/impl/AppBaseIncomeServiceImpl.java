package com.luck.dataService.service.impl;

import com.luck.dataDao.AppBaseIncomeDao;
import com.luck.dataService.service.AppBaseIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppBaseIncomeServiceImpl implements AppBaseIncomeService {
    @Autowired
    AppBaseIncomeDao appBaseIncomeDao;
    @Override
    public List<Map> query() {
        List<Map> appBaseIncomeList = appBaseIncomeDao.query();
        return appBaseIncomeList;
    }
}
