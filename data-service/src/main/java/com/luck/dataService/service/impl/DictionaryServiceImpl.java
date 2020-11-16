package com.luck.dataService.service.impl;

import com.luck.dataDao.AppBaseEducationDao;
import com.luck.dataDao.AppBaseIncomeDao;
import com.luck.dataService.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    AppBaseEducationDao appBaseEducationDao;
    @Autowired
    AppBaseIncomeDao appBaseIncomeDao;

    @Override
    public List<Map> queryByType(String type) {
        List<Map> listMap = new ArrayList<>();
        if(!"".equals(type) && type != null){
            if("income".equals(type)){
                listMap = appBaseIncomeDao.query();
            }else if("education".equals(type)){
                listMap = appBaseEducationDao.query();
            }
        }
        return listMap;
    }
}
