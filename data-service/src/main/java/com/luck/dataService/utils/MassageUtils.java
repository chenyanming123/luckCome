package com.luck.dataService.utils;

import java.util.HashMap;
import java.util.Map;

public class MassageUtils {
    //返回状态信息
    public static Map getMsg(String code,String msg){
        Map map = new HashMap();
        map.put("code",code);
        map.put("msg",msg);
        return map;
    }
}
