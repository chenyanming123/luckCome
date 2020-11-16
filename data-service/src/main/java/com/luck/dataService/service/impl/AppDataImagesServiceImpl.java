package com.luck.dataService.service.impl;

import com.luck.dataDao.AppDataImagesDao;
import com.luck.dataEntity.AppDataImages;
import com.luck.dataService.service.AppDataImagesService;
import com.luck.dataService.utils.MassageUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AppDataImagesServiceImpl implements AppDataImagesService {
    Logger logger = LoggerFactory.getLogger(AppDataImagesServiceImpl.class);
    @Value("${upload.path:}")
    String uploadPath;
    @Autowired
    AppDataImagesDao appDataImagesDao;

    private String getUploadPath() {
        String path = uploadPath;
        if (StringUtils.isEmpty(path)) {
            path = new File(SystemUtils.getUserDir(), "upload").getAbsolutePath();
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
//        String dateStr = String.format("%s%02d", year, month);
        String dateStr = "userinfo";
        String subDir = "images";
        path = Paths.get(path, subDir, dateStr).toAbsolutePath().toString();
        logger.info("upload file to {}", path);
        new File(path).mkdirs();
        return path;
    }
    @Override
    public Map uploadUserInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map map = new HashMap();
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        if(((MultipartHttpServletRequest)request).getFiles("files") != null){
            List<MultipartFile> files = ((MultipartHttpServletRequest)request).getFiles("files");
            for (int i = 0;i<files.size();i++) {
                MultipartFile multipartFile = files.get(i);
                //返回的是字节，1M=1024KB=1048576字节 1KB=1024Byte
                Long length = multipartFile.getSize();
                String fileName = multipartFile.getOriginalFilename();
                System.out.println(fileName);
                //获取文件后缀名
//            String suffix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase().trim();
                String path = getUploadPath();
                File saveFile = new File(path, fileName);
                multipartFile.transferTo(saveFile);
                //获取表单参数
                Integer userId = Integer.parseInt(request.getParameter("userId"));
                AppDataImages appDataImages = new AppDataImages();
                appDataImages.setUserId(userId);
                appDataImages.setImageName(fileName);
                appDataImages.setImagePath(path);
                appDataImages.setCreateTime(new Date());
                appDataImages.setRegisterTime(new Date());
                appDataImagesDao.insert(appDataImages,true);
                map = MassageUtils.getMsg("200","请求成功！");
                map.put("imageName",fileName);
                map.put("fileId",appDataImages.getId());
            }
        }else{
            map = MassageUtils.getMsg("601","文件为空！");
        }
        return map;
    }

    @Override
    @Transactional
    public Void deleteImages(String deleteFileIds) {
        if(!"".equals(deleteFileIds) && deleteFileIds != null){
            String[] fileIds = deleteFileIds.split(",",-1);
            for(int i =0;i<fileIds.length;i++){
                AppDataImages appDataImages = appDataImagesDao.unique(Integer.parseInt(fileIds[i]));
                appDataImagesDao.deleteById(appDataImages.getId());
                File file = new File(appDataImages.getImagePath()+"/"+appDataImages.getImageName());
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        return null;
    }

}
