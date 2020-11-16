package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataUserinfoUtil;
import com.luck.dataService.service.AppDataUserinfoService;
import io.swagger.annotations.*;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/appDataUserinfo",produces = {"application/json"})
@Api(value = "用户资料信息", description = "用户资料信息的增、查、改、删等")
public class AppDataUserinfoController {
    @Autowired
    private AppDataUserinfoService appDataUserinfoService;

    @ApiOperation(value = "添加或修改用户资料信息", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/addOrUpdataUserInfo", method = RequestMethod.POST)
    public ResponseEntity<Map> addOrUpdataUserInfo(@RequestBody AppDataUserinfoUtil appDataUserinfoUtil) throws Exception {
        return ResponseEntity.ok(appDataUserinfoService.addOrUpdataUserInfo(appDataUserinfoUtil));
    }

//    @ApiOperation(value = "添加用户资料信息", notes = "", authorizations = {@Authorization(value = "token")})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
//    @RequestMapping(value = "/addUserInfo", method = RequestMethod.POST)
//    public ResponseEntity<Map> addUserInfo(@RequestBody AppDataUserinfo appDataUserinfo) throws Exception {
//        return ResponseEntity.ok(appDataUserinfoService.addUserInfo(appDataUserinfo));
//    }
//
//    @ApiOperation(value = "修改用户资料信息", notes = "", authorizations = {@Authorization(value = "token")})
//    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
//    @RequestMapping(value = "/updataUserInfo", method = RequestMethod.POST)
//    public ResponseEntity<Map> updataUserInfo(@RequestBody AppDataUserinfo appDataUserinfo) throws Exception {
//        return ResponseEntity.ok(appDataUserinfoService.updataUserInfo(appDataUserinfo));
//    }
    @ApiOperation(value = "查看用户资料信息(含上传的图片资料)", notes = "")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> getUserInfo(@RequestParam(required = true) Integer queryUserId,
                                                  @RequestParam(required = false) Integer userId) throws Exception {
        return ResponseEntity.ok(appDataUserinfoService.getUserInfo(queryUserId,userId));
    }


//    @ApiOperation(value = "分页列表查看用户资料信息", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiOperation(value = "分页列表查看用户资料信息", notes = "")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryUserInfo", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> queryUserInfo(@RequestParam(required = true) Integer pageNum,
                                                               @RequestParam(required = true) Integer pageSize,
                                                               @RequestParam(required = false) Integer userId,
                                                               @RequestParam(required = false) String address,
                                                               @RequestParam(required = false) Integer age_start,
                                                               @RequestParam(required = false) Integer age_end) throws Exception {
        return ResponseEntity.ok(appDataUserinfoService.queryUserInfo(pageNum,pageSize,userId,address,age_start,age_end));
    }
}
