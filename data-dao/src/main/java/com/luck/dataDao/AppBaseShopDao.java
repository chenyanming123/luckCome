package com.luck.dataDao;

import com.luck.dataEntity.AppBaseShop;
import org.beetl.sql.core.annotatoin.Param;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.Map;

public interface AppBaseShopDao extends BaseMapper<AppBaseShop> {
    //获取商店的部分信息
    Map getAppBaseShop(@Param("id")Integer id);
}

