package com.luck.dataWeb.controller;

import com.luck.dataEntity.AppBaseIncome;
import com.luck.dataService.service.AppBaseIncomeService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/appBaseIncome",produces = {"application/json"})
@Api(value = "收入字典", description = "收入字典的增、查、改、删等")
public class AppBaseIncomeController {
    @Autowired
    private AppBaseIncomeService appBaseIncomeService;

    @ApiOperation(value = "查询收入字段数据", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<List<Map>> query() throws Exception {
        return ResponseEntity.ok(appBaseIncomeService.query());
    }
}
