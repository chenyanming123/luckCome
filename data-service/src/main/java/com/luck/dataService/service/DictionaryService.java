package com.luck.dataService.service;

import java.util.List;
import java.util.Map;

public interface DictionaryService {
    //查询各类数据字典
    List<Map> queryByType(String type);
}
