package com.luck.dataDao;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.SysRole;
import org.beetl.sql.core.mapper.BaseMapper;

import java.util.List;

public interface SysRoleDao extends BaseMapper<SysRole> {
    /**
     * 查询所有角色信息，返回id和ChineseName
     * @param
     */
    List<JSONObject> getListBack_Id_ChineseName();
}
