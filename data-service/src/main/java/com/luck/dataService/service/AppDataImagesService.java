package com.luck.dataService.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public interface AppDataImagesService {
    //上传用户图片资料信息
    Map uploadUserInfo(HttpServletRequest request, HttpServletResponse response)throws Exception;
    //删除用户图片资料信息
    Void deleteImages(String deleteFileIds);
}
