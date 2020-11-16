package com.luck.dataWeb.controller;

import com.luck.dataEntity.LoginUser;
import com.luck.dataEntity.SysUser;
import com.luck.dataEntity.TokenUser;
import com.luck.dataService.common.ErrCode;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.manager.TokenManager;
import com.luck.dataService.service.impl.AccountService;
import com.luck.dataService.utils.MassageUtils;
import com.luck.dataService.utils.WebUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping(value = "/login",produces = {"application/json"})
@Api(value = "权限", description = "权限相关接口，登录等")
public class LoginController {
//    @Autowired
//    private SysUserService sysUserService;
//    @Autowired
//    private RedisService redisService;
    @Autowired
    TokenManager tokenManager;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AccountService accountService;

    @ApiOperation(value = "登录", notes = "登录")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<Map> login(
            @RequestParam(required = true) String userName,
            @RequestParam(required = true) String password )
            throws ApiException {
//        SysUser sysUser = sysUserService.getUserByUserNameAndPassword(userName,password);
        SysUser sysUser = accountService.getSysUserByLoginNameAndPassword(userName,password);
        if(sysUser == null){
            throw new ApiException(ErrCode.noUser);
        }
        //生成token,并将token存在redis中
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUsername(sysUser.getUserName());
        String loginIp = WebUtils.getRemoteAddr(request);
        tokenUser.setLastIp(loginIp);
        tokenUser.setUserId(sysUser.getId());
        String token = tokenManager.generateToken(tokenUser);
        //返回用户信息
        LoginUser loginUser = new LoginUser();
        loginUser.setId(sysUser.getId());
        loginUser.setRealName(sysUser.getRealName());
        loginUser.setRoles(accountService.getRoles(sysUser));
        loginUser.setToken(token);
        Map map = MassageUtils.getMsg("200","登录成功");
        map.put("content",loginUser);
        return ResponseEntity.ok(map);
    }
    @ApiOperation(value = "注册", notes = "注册")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<Object>  register(
            @RequestParam(required = true) String realName,
            @RequestParam(required = true) String userName,
            @RequestParam(required = true) String password,
            @RequestParam(required = true) String mobile) throws ApiException {
        return ResponseEntity.ok(accountService.registerSysUser(realName, userName,password, mobile));
    }
    @ApiOperation(value = "登出", notes = "登出", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ResponseEntity<Void> logout() throws Exception {
        SysUser hzzUser = accountService.getCurrentUser(request);
        TokenUser tokenUser = new TokenUser();
        tokenUser.setUserId(hzzUser.getId());
        tokenManager.deleteToken(tokenUser);
        return ResponseEntity.ok(null);
    }
    @ApiOperation(value = "测试", notes = "测试")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/httpTest", method = RequestMethod.POST)
    public ResponseEntity<Object>  httpTest() {
        System.out.println("进入方法");
        return ResponseEntity.ok("返回内容");
    }
}
