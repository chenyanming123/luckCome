package com.luck.dataWeb.controller;

import com.luck.dataEntity.AppBaseEducation;
import com.luck.dataService.service.AppBaseEducationService;
import com.luck.dataService.service.AppBaseIncomeService;
import com.luck.dataService.service.DictionaryService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/dictionary",produces = {"application/json"})
@Api(value = "数据字典", description = "数据字典的增、查、改、删等")
public class DictionaryController {
    @Autowired
    private DictionaryService dictionaryService;

    @ApiOperation(value = "查询各类数据字典数据", notes = "", authorizations = {@Authorization(value = "token")})
    @ApiResponses(value = {@ApiResponse(code = 200, message = "successful")})
    @RequestMapping(value = "/queryByType", method = RequestMethod.POST)
    public ResponseEntity<List<Map>> query(@RequestParam(required = true) String type) throws Exception {
        return ResponseEntity.ok(dictionaryService.queryByType(type));
    }
}
