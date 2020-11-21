package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataEntity.AppDataAppointment;
import com.luck.dataService.service.AppDataAppointmentService;
import io.swagger.annotations.*;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/appDataAppointment",produces = {"application/json"})
@Api(value = "约会记录", description = "约会记录的增、查、改、删等")
public class AppDataAppointmentController {
    @Autowired
    private AppDataAppointmentService appDataAppointmentService;

    @ApiOperation(value = "发起约会", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/addAppDataAppointment", method = RequestMethod.POST)
    public ResponseEntity<Map> addAppDataAppointment(@RequestBody AppDataAppointment appDataAppointment) {
        return ResponseEntity.ok(appDataAppointmentService.addAppDataAppointment(appDataAppointment));
    }

    @ApiOperation(value = "同意邀约并约定唯一的时间、地点1111", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/updateAppDataAppointment", method = RequestMethod.POST)
    public ResponseEntity<Map> addAppDataAppointment(@RequestParam Integer id,
                                                     @RequestParam String appointmentTime,
                                                     @RequestParam String placeId) {
        return ResponseEntity.ok(appDataAppointmentService.updateAppDataAppointment(id,appointmentTime,placeId));
    }


    @ApiOperation(value = "删除约会记录", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/deleteAppDataAppointment", method = RequestMethod.POST)
    public ResponseEntity<Map> deleteAppDataAppointment(@RequestParam Integer id) {
        return ResponseEntity.ok(appDataAppointmentService.deleteAppDataAppointment(id));
    }


    @ApiOperation(value = "不感兴趣", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/loseInterest", method = RequestMethod.POST)
    public ResponseEntity<Map> loseInterest(@RequestParam Integer id){
        return ResponseEntity.ok(appDataAppointmentService.loseInterest(id));
    }
    @ApiOperation(value = "取消约会", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/cancelAppointment", method = RequestMethod.POST)
    public ResponseEntity<Map> cancelAppointment(@RequestParam Integer id){
        return ResponseEntity.ok(appDataAppointmentService.cancelAppointment(id));
    }

    /*************************************/
    @ApiOperation(value = "我喜欢的", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryMylove", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> queryMylove(@RequestParam(required = true) Integer pageNum,
                                                             @RequestParam(required = true) Integer pageSize,
                                                             @RequestParam Integer userId) {
        return ResponseEntity.ok(appDataAppointmentService.queryMylove(pageNum,pageSize,userId));
    }
    @ApiOperation(value = "喜欢我的", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryLoveme", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> queryLoveme(@RequestParam(required = true) Integer pageNum,
                                                             @RequestParam(required = true) Integer pageSize,
                                                             @RequestParam Integer userId){
        return ResponseEntity.ok(appDataAppointmentService.queryLoveme(pageNum,pageSize,userId));
    }
    @ApiOperation(value = "相互喜欢的", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryLoveEachOther", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> queryLoveEachOther(@RequestParam(required = true) Integer pageNum,
                                                                    @RequestParam(required = true) Integer pageSize,
                                                                    @RequestParam Integer userId){
        return ResponseEntity.ok(appDataAppointmentService.queryLoveEachOther(pageNum,pageSize,userId));
    }
    @ApiOperation(value = "拒绝邀约的", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/refuseAppointment", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> refuseAppointment(@RequestParam(required = true) Integer pageNum,
                                                                   @RequestParam(required = true) Integer pageSize,
                                                                   @RequestParam Integer userId){
        return ResponseEntity.ok(appDataAppointmentService.refuseAppointment(pageNum,pageSize,userId));
    }

}
