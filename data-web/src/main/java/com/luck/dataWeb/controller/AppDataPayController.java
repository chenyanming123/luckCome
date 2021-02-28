package com.luck.dataWeb.controller;

import com.luck.dataEntity.AppDataPay;
import com.luck.dataService.service.AppDataPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/AppDataPay",produces = {"application/json"})
@Api(value = "用户支付信息", description = "用户支付信息的增、查、改、删等")
public class AppDataPayController {
    @Autowired
    private AppDataPayService appDataPayService;

    @ApiOperation(value = "添加用户支付信息", notes = "")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/addPayInfo", method = RequestMethod.POST)
    public ResponseEntity<Integer> addPayInfo(@RequestBody AppDataPay appDataPay) {
        return ResponseEntity.ok(appDataPayService.addPayInfo(appDataPay));
    }
}
