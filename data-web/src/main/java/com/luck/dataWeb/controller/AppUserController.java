package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppUser;
import com.luck.dataService.service.AppUserService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/appUser",produces = {"application/json"})
@Api(value = "小程序用户", description = "小程序用户的增、查、改、删等")
public class AppUserController {
    @Autowired
    private AppUserService appUserService;

    @ApiOperation(value = "获取用户信息", notes = "将前端提交的用户信息新增到数据库或者查询已存在的用户信息，最后返回库中存的用户信息", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseEntity<AppUser> getUserInfo(@RequestBody(required = true) JSONObject userInfo) {
        return ResponseEntity.ok(appUserService.getUserInfo(userInfo));
    }
//    @ApiOperation(value = "获取用户信息", notes = "通过手机号获取用户信息", authorizations = {@Authorization(value = "token")})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
//    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
//    public ResponseEntity<AppUser> getUserInfo(@RequestBody(required = true) JSONObject mobile) {
//        return ResponseEntity.ok(appUserService.getUserInfo(mobile));
//    }
}
