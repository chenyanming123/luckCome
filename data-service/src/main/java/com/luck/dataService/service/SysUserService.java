package com.luck.dataService.service;

import com.luck.dataEntity.SysUser;
import com.luck.dataService.exception.ApiException;
import org.beetl.sql.core.engine.PageQuery;

import java.util.List;

public interface SysUserService {
    //增
    Object insert(SysUser sysUser) throws ApiException;
    //改
    Object update(SysUser sysUser) throws ApiException;
    //查列表
    PageQuery<SysUser> find(int pageNumber, int pageSize, String searchString, String sortString);
    //删多个
    Object delete(List<Integer> idList);
    //查单个详情
    SysUser get(Integer id);

    SysUser getUserByUserNameAndPassword(String userName, String password) throws ApiException;
}
