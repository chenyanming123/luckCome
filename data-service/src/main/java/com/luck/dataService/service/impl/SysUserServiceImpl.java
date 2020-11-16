package com.luck.dataService.service.impl;

import com.luck.dataDao.SysUserDao;
import com.luck.dataEntity.SysUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.service.SysUserService;
import com.luck.dataService.utils.MassageUtils;
import com.luck.dataService.utils.RSqlUtils;
import org.beetl.sql.core.engine.PageQuery;
import org.beetl.sql.core.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;


@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    SysUserDao sysUserDao;
    @Autowired
    AccountService accountService;
    private Logger logger = LoggerFactory.getLogger(SysUserService.class);

    @Override
    public Object insert(SysUser sysUser) throws ApiException {
        //判断账户是否存在
        SysUser sysUserOld = accountService.getSysUserByLoginName(sysUser.getUserName());
        if (sysUserOld != null)
            throw new ApiException(ErrCode.existUserName);
        //密码加密
        accountService.setPassword(sysUser,sysUser.getPassword());
        sysUser.setRegisterTime(new Date());
        sysUserDao.insert(sysUser);
        return MassageUtils.getMsg("200","添加成功");
    }

    @Override
    public Object update(SysUser sysUser) throws ApiException {
        SysUser sysUser1 = sysUserDao.unique(sysUser.getId());
        if(sysUser.getPassword() != null)
            //密码加密
            accountService.setPassword(sysUser,sysUser.getPassword());
        sysUser.setUserName(sysUser1.getUserName());
        sysUser.setRegisterTime(new Date());
        sysUserDao.updateById(sysUser);
        return MassageUtils.getMsg("200","修改成功");
    }

    @Override
    public PageQuery<SysUser> find(int pageNumber, int pageSize, String searchString, String sortString) {
        boolean offsetStartZero = sysUserDao.getSQLManager().isOffsetStartZero();
        Query<SysUser> query = sysUserDao.getSQLManager().query(SysUser.class);
        query = RSqlUtils.getQueryByString(query, SysUser.class, searchString, sortString);
        PageQuery<SysUser> pageQuery = new PageQuery<>(pageNumber, pageSize);
        Query<SysUser> countQuery = sysUserDao.getSQLManager().query(SysUser.class);
        countQuery.setSql(query.getSql());
        countQuery.addParam(query.getParams());
        Long count = countQuery.count();
        List<SysUser> list = null;
        if (count == 0) {
            list = Collections.emptyList();
        } else {
            long offset = (pageNumber - 1) * pageSize + (offsetStartZero ? 0 : 1);
            list = query.limit(offset, pageSize).select();
        }
        pageQuery.setTotalRow(count);
        pageQuery.setList(list);
        return pageQuery;
    }

    @Override
    public Object delete(List<Integer> idList) {
        for (Integer id : idList)
            sysUserDao.deleteById(id);
        return MassageUtils.getMsg("200","删除成功");
    }

    @Override
    public SysUser get(Integer id) {
        return sysUserDao.unique(id);
    }

    @Override
    public SysUser getUserByUserNameAndPassword(String userName, String password) throws ApiException {
        SysUser user = getUserByUserName(userName);
        if (user != null)
            if(!user.getPassword().equals(password))
                throw new ApiException(ErrCode.errPassword);
        return user;
    }

    //根据用户帐号查询用户信息
    public SysUser getUserByUserName(String userName){
        SysUser user = null;
        if (StringUtils.isEmpty(userName))
            return user;
        try {
            user = sysUserDao.createLambdaQuery().orEq(SysUser::getUserName, userName).unique();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("登录名重复或不存在");
        }
        return user;
    }
}
