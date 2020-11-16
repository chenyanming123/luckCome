package com.luck.dataWeb.controller;

import com.luck.dataEntity.AppBaseEducation;
import com.luck.dataService.service.AppBaseEducationService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/appBaseEducation",produces = {"application/json"})
@Api(value = "学历字典", description = "学历字典的增、查、改、删等")
public class AppBaseEducationController {
    @Autowired
    private AppBaseEducationService appBaseEducationService;

    @ApiOperation(value = "查询学历字段数据", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseEntity<List<Map>> query() throws Exception {
        return ResponseEntity.ok(appBaseEducationService.query());
    }
}
