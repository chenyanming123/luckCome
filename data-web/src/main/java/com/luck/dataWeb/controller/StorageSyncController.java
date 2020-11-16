package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataService.exception.ApiException;
import com.luck.dataService.service.StorageSyncService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/storageSync",produces = {"application/json"})
@Api(value = "登录态", description = "通过code获取登录态")
public class StorageSyncController {

    @Autowired
    private StorageSyncService storageSyncService;


    @ApiOperation(value = "微信/抖音 获取登录态", notes = "微信/抖音 获取登录态")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/getCode2Session", method = RequestMethod.POST)
    public ResponseEntity<Map> getCode2Session(@RequestBody(required = true) JSONObject code)throws ApiException {
        return ResponseEntity.ok(storageSyncService.getCode2Session(code));
    }
//    @ApiOperation(value = "测试验证登录态是否有效", notes = "测试验证登录态是否有效",authorizations = {@Authorization(value = "token")})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
//    @RequestMapping(value = "/testToken", method = RequestMethod.POST)
//    public ResponseEntity<String> testToken()throws ApiException{
//        return ResponseEntity.ok("12321312");
//    }
    @ApiOperation(value = "通过encryptedData、session_key、iv获取用户信息", notes = "通过encryptedData、session_key、iv获取用户信息",authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseEntity<String> getUserInfo(
            @RequestParam String encryptedData,
            @RequestParam String session_key,
            @RequestParam String iv
    )throws ApiException{
        storageSyncService.getUserInfo(encryptedData,session_key,iv);
        return ResponseEntity.ok("12321312");
    }
}
