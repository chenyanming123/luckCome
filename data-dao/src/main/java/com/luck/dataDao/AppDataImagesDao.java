package com.luck.dataDao;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataImages;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface AppDataImagesDao extends BaseMapper<AppDataImages> {
    /**
     * 通过userId查询图片信息，返回id和imageName
     * @param
     */
    List<JSONObject> getImagesByUserId(@Param("userId") Integer userId);

}
