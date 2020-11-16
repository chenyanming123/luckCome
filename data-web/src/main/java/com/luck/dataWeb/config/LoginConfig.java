package com.luck.dataWeb.config;

import com.luck.dataEntity.TokenUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.common.ErrorResp;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.manager.TokenManager;
import com.luck.dataService.service.SysUserService;
import com.luck.dataService.service.impl.AccountService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginConfig implements HandlerInterceptor {
    private Logger logger = LoggerFactory.getLogger(SysUserService.class);
    @Autowired
    TokenManager tokenManager;
    @Autowired
    AccountService accountService;
    /**
     * 访问方法之前
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        try {
//            System.out.println("===================自定义拦截器=================");
            if (handler instanceof HandlerMethod) {
                String requestURI = request.getRequestURI();
//                logger.info("当前请求路径是:{}", requestURI);
                String token = request.getHeader("token");
                if (StringUtils.isNotEmpty(token)) {
                    TokenUser tokenUser = tokenManager.getTokenUserFromToken(token);
                    Integer userId = tokenUser.getUserId();
                    //保存全局用户信息
                    accountService.setCurrentUserId(request, userId);
                }
                //后台登录检测
                checkAdminWebTokenAndPerm(request);
                return true;
            }
        }catch (ApiException e){
            int responseStatus = HttpServletResponse.SC_UNAUTHORIZED;
            int[] internalEcs = new int[]{
                    ErrCode.noApiOperation.getCode(),
                    ErrCode.noPermission.getCode(),
                    ErrCode.nullToken.getCode(),
            };
            if (!ArrayUtils.contains(internalEcs, e.getCode()))
                responseStatus = HttpServletResponse.SC_BAD_REQUEST;
            ErrorResp.respErrorApi(response, e, responseStatus);
            return false;
        }
        return true;
    }
    private void checkAdminWebTokenAndPerm(HttpServletRequest request) throws Exception {
        //后台登录检测
        String token = request.getHeader("token");
        // 获取api_key,即token,token的所有失败情况由异常处理
        if (StringUtils.isEmpty(token)) {
            throw new ApiException(ErrCode.nullToken);
        }
        // 判断token本身和redis的token是否有效
        TokenUser tokenUser = tokenManager.getTokenUserFromToken(token);
        Integer userId = tokenUser.getUserId();
        // 更新redis token失效时间
        tokenManager.refreshToken(tokenUser);
        //保存全局用户信息
        accountService.setCurrentUserId(request, userId);
    }
}
