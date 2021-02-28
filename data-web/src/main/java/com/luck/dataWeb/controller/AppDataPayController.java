package com.luck.dataWeb.controller;

import com.luck.dataEntity.AppDataPay;
import com.luck.dataService.service.AppDataPayService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/AppDataPay",produces = {"application/json"})
@Api(value = "用户支付信息", description = "用户支付信息的增、查、改、删等")
public class AppDataPayController {
    @Autowired
    private AppDataPayService appDataPayService;

    @ApiOperation(value = "添加用户支付信息", notes = "",authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/addPayInfo", method = RequestMethod.POST)
    public ResponseEntity<Map> addPayInfo(@RequestParam (required = true) Integer appointmentId,
                                          @RequestParam (required = true) Integer userId,
                                          @RequestParam (required = true) Integer payMoney) {
        return ResponseEntity.ok(appDataPayService.addPayInfo(appointmentId,userId,payMoney));
    }
}
