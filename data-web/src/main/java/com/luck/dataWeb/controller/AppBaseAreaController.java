package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataService.service.AppBaseAreaService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/appBaseArea",produces = {"application/json"})
@Api(value = "全国行政区划", description = "全国行政区划的增、查、改、删等")
public class AppBaseAreaController {
    @Autowired
    private AppBaseAreaService appBaseAreaService;

    @ApiOperation(value = "获取行政区划信息", notes = "根据行政区划查询下级行政区划，行政区划为空是返回省级行政区划", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryArea", method = RequestMethod.POST)
    public ResponseEntity<List<JSONObject>> queryArea(@RequestBody(required = false) JSONObject areaCode) {
        return ResponseEntity.ok(appBaseAreaService.queryAreaByAreaCode(areaCode));
    }
    
}
