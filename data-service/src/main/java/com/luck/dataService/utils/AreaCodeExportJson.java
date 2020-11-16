package com.luck.dataService.utils;

import com.alibaba.fastjson.JSON;
import com.luck.dataService.service.impl.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *  生成json文件工具类，
 *  生成行政区划的json文件
 */
public class AreaCodeExportJson {

    private static Logger logger = LoggerFactory.getLogger(AccountService.class);
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<>();
        List<Object> list = new ArrayList<>();
        String [] strs = {
                "近三年公务员年度考核结果曾被定为基本称职",
                "近三年111公务员年度考核结果曾被定为基本称职",
                "近三年2公务员年度考核结果曾被定为基本称职"
        };
        for(int i=0; i< strs.length; i++){
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("id", "00" + (i + 1));
            infoMap.put("context", strs[i]);
            list.add(infoMap);
        }
        map.put("data", list);
        //获取文件的绝对路径        根路径
        //        String filePath = ResourceUtils.getURL("classpath:").getPath();
        String fileName = "areaInfo";
        String jsonString = JSON.toJSONString(map);

        // 拼接文件完整路径// 生成json格式文件
        //        String fullPath = filePath + File.separator + fileName + ".json";

        boolean success = AreaCodeExportJson.createJSONFile(map, fileName);
        if (success){
            logger.info("ok");
        }else {
            logger.info("error");
        }
    }

    //封装创建json文件的方法
    public static boolean createJSONFile(Object obj, String fileName){
        boolean flag = true;
        try {
            //获取文件的绝对路径        根路径
//            String filePath = ResourceUtils.getURL("classpath:").getPath();
            String filePath = "D://";
            //            String fileName = "app";
            String jsonString = JSON.toJSONString(obj);
            // 拼接文件完整路径// 生成json格式文件
            String fullPath = filePath + File.separator + fileName + ".json";
            // 保证创建一个新文件
            File file = new File(fullPath);
            if (!file.getParentFile().exists()) { // 如果父目录不存在，创建父目录
                file.getParentFile().mkdirs();
            }
            if (file.exists()) { // 如果已存在,删除旧文件
                file.delete();
            }
            file.createNewFile();//创建新文件

            if(jsonString.indexOf("'")!=-1){
                //将单引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("'", "\\'");
            }
            if(jsonString.indexOf("\"")!=-1){
                //将双引号转义一下，因为JSON串中的字符串类型可以单引号引起来的
                jsonString = jsonString.replaceAll("\"", "\\\"");
            }

            if(jsonString.indexOf("\r\n")!=-1){
                //将回车换行转换一下，因为JSON串中字符串不能出现显式的回车换行
                jsonString = jsonString.replaceAll("\r\n", "\\u000d\\u000a");
            }
            if(jsonString.indexOf("\n")!=-1){
                //将换行转换一下，因为JSON串中字符串不能出现显式的换行
                jsonString = jsonString.replaceAll("\n", "\\u000a");
            }
            // 将格式化后的字符串写入文件
            Writer write = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            logger.info("json文件内容：" + jsonString);
            write.write(jsonString);
            logger.info("文件创建成功！");
            write.flush();
            write.close();
        } catch (Exception e) {
            flag = false;
            e.printStackTrace();
        }
        return flag;
    }
}
