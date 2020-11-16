package com.luck.dataWeb.controller;

import com.alibaba.fastjson.JSONObject;
import com.luck.dataService.service.AppBaseShopService;
import io.swagger.annotations.*;
import org.beetl.sql.core.engine.PageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/appBaseShop",produces = {"application/json"})
@Api(value = "商店信息", description = "商店信息的增、查、改、删等")
public class AppBaseShopController {
    @Autowired
    private AppBaseShopService appBaseShopService;

    @ApiOperation(value = "商铺信息列表", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/getShopList", method = RequestMethod.POST)
    public ResponseEntity<PageQuery<JSONObject>> getShopList(@RequestParam(required = true) Integer pageNum,
                                                                   @RequestParam(required = true) Integer pageSize,
                                                                   @RequestParam(required = false) String shopName,
                                                                   @RequestParam(required = false) String shopType){
        return ResponseEntity.ok(appBaseShopService.getShopList(pageNum,pageSize,shopName,shopType));
    }
}
