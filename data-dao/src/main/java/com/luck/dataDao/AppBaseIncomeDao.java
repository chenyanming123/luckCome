package com.luck.dataDao;

import com.luck.dataEntity.AppBaseIncome;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface AppBaseIncomeDao extends BaseMapper<AppBaseIncome> {
    //查询收入字典
    List<Map> query();
}
