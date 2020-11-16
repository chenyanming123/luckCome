package com.luck.dataWeb.controller;

import com.luck.dataService.service.AppDataImagesService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping(value = "/appDataImages",produces = {"application/json"})
@Api(value = "用户图片资料信息", description = "用户图片资料信息的增、查、改、删等")
public class AppDataImagesController {
    @Autowired
    private AppDataImagesService appDataImagesService;

    @ApiOperation(value = "上传用户图片资料信息", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/uploadImages", method = RequestMethod.POST)
    public ResponseEntity<Map> uploadUserInfo(HttpServletRequest request,
                                              HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(appDataImagesService.uploadUserInfo(request,response));
    }
}
