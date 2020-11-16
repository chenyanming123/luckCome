package com.luck.dataService.service.impl;

import com.luck.dataDao.AppBaseEducationDao;
import com.luck.dataEntity.AppBaseEducation;
import com.luck.dataService.service.AppBaseEducationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppBaseEducationServiceImpl implements AppBaseEducationService {
    @Autowired
    AppBaseEducationDao appBaseEducationDao;

    @Override
    public List<Map> query() {
        List<Map> appBaseEducationList = appBaseEducationDao.query();
        return appBaseEducationList;
    }
}
