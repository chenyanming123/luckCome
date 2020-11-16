package com.luck.dataService.service.impl;

import com.luck.dataDao.SysRoleDao;
import com.luck.dataDao.SysUserDao;
import com.luck.dataEntity.SysRole;
import com.luck.dataEntity.SysUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.utils.MassageUtils;
import com.luck.dataService.utils.WebUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 账户服务
 */
@Service
public class AccountService {
    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private int baseTimeout = 3600; //key默认失效时间

    private Logger logger = LoggerFactory.getLogger(AccountService.class);

    public void setCurrentUserId(HttpServletRequest request, Integer userId) {
        SysUser sysUser = sysUserDao.unique(userId);
        request.setAttribute("currentUser", sysUser);
        request.setAttribute("currentUserId", userId);
    }

    public Integer getCurrentUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("currentUserId");
    }
    public SysUser getCurrentUser(HttpServletRequest request) {
        return (SysUser) request.getAttribute("currentUser");
    }
    public Object registerSysUser(String realName,String userName, String password, String mobile) throws ApiException {
        if (StringUtils.isBlank(realName) || StringUtils.isBlank(realName) || StringUtils.isBlank(password)) {
            throw new ApiException(ErrCode.param);
        }
        //电话验证
        if (!WebUtils.isMobileNumber(mobile)) {
            throw new ApiException(ErrCode.invalidMobileNumber);
        }
        //查询账户是否存在
        SysUser sysUser = getSysUserByLoginName(userName);
        if (sysUser != null)
            throw new ApiException(ErrCode.existUserName);
        //获取默认角色
        SysRole sysRole = sysRoleDao.createLambdaQuery().orEq(SysRole::getName, "common").unique();
        if (sysRole == null)
            throw new ApiException(ErrCode.noRole);
        sysUser = new SysUser();
        sysUser.setUserName(userName);
        sysUser.setRealName(realName);
        sysUser.setMobile(mobile);
        sysUser.setRoles(sysRole.getName());
        sysUser.setRegisterTime(new Date());
        setPassword(sysUser, password);
        sysUserDao.insert(sysUser);
        return MassageUtils.getMsg("200","注册成功");
    }
    public SysUser getSysUserByLoginName(String loginName) throws ApiException {
        SysUser sysUser = null;
        if (StringUtils.isEmpty(loginName))
            return sysUser;
        try {
//            sysUser = sysUserDao.createLambdaQuery().orEq(SysUser::getMobile, loginName).orEq(SysUser::getUserName, loginName).unique();
            sysUser = sysUserDao.createLambdaQuery().orEq(SysUser::getUserName, loginName).unique();
        } catch (Exception e) {
            logger.error("登录名重复或不存在");
        }
        return sysUser;
    }
    public void setPassword(SysUser sysUser, String password) {
        sysUser.setPassword(encryptPassword(password));
    }

    private String encryptPassword(String password) {
//        return new String(DigestUtils.md5(DigestUtils.md5(password)));
        return new String(DigestUtils.md5Hex(DigestUtils.md5Hex(password)));
    }
    public String[] getRoles(SysUser sysUser) {
        String rolesStr = sysUser.getRoles();
        if (StringUtils.isEmpty(rolesStr))
            return new String[0];
        return StringUtils.split(rolesStr, ",");
    }
    public SysUser getSysUserByLoginNameAndPassword(String loginName, String password) throws ApiException {
        SysUser sysUser = getSysUserByLoginName(loginName);
        if (sysUser != null) {
            checkAlreadyLoginErrorPassword(sysUser.getId());
            if (!encryptPassword(password).equals(sysUser.getPassword())) {
                loginByErrorPassword(sysUser.getId());
            }
            redisTemplate.delete(getLoginByErrorPasswordKey(sysUser.getId()));
        }
        return sysUser;
    }
    private void checkAlreadyLoginErrorPassword(Integer userId) throws ApiException {
        int seconds = getLoginByErrorPasswordTimeout(userId);
        if (seconds > baseTimeout) {
            String value = getLoginByErrorPasswordErrCount(userId);
            Integer errCount = Integer.valueOf(value);
            if (errCount < 5)
                return;
            String msg = String.format("连续%s次密码错误，请%s秒后再尝试登录", errCount, seconds - baseTimeout);
            throw new ApiException(ErrCode.errPassword, msg);
        }
    }
    private String getLoginByErrorPasswordErrCount(Integer userId) {
        String key = getLoginByErrorPasswordKey(userId);
        String value = redisTemplate.boundValueOps(key).get();
        return value;
    }
    private Integer getLoginByErrorPasswordTimeout(Integer userId) {
        String key = getLoginByErrorPasswordKey(userId);
        int timeoutSec = Math.toIntExact(redisTemplate.boundValueOps(key).getExpire());
        return timeoutSec;
    }
    private String getLoginByErrorPasswordKey(Integer userId) {
        return "admin_login_fail_" + userId;
    }
    private void loginByErrorPassword(Integer userId) throws ApiException {
        String value = getLoginByErrorPasswordErrCount(userId);
        int count;
        int timeoutSec;
        if (value == null) {
            count = 1;
            timeoutSec = baseTimeout;
        } else {
            count = Integer.parseInt(value) + 1;
            timeoutSec = getLoginByErrorPasswordTimeout(userId);
            if (timeoutSec < baseTimeout)
                timeoutSec = baseTimeout;
        }
        String msg = String.format("连续%s次密码错误，超过5次后会被锁定一段时间", count);
        // 输入错误超过5次后，开始冻结
        if (count == 5)
            timeoutSec += 60;
        if (count == 6)
            timeoutSec += 60 * 5;
        if (count > 6) {
            timeoutSec += 60 * Math.pow(2, count - 2);
        }
        if (count > 5)
            msg = String.format("连续%s次密码错误，请%s秒后再尝试登录", count, timeoutSec - baseTimeout);

        String key = getLoginByErrorPasswordKey(userId);
        redisTemplate.boundValueOps(key).set(String.valueOf(count), timeoutSec, TimeUnit.SECONDS);
        if (count > 3) {
            throw new ApiException(ErrCode.errPassword, msg);
        }
        else {
            throw new ApiException(ErrCode.errPassword);
        }

    }
}
