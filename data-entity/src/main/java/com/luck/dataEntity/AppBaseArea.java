package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

public class AppBaseArea {
    @ApiModelProperty(value = "", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "行政区划代码",position = 2)
    private String areaCode ;
    @ApiModelProperty(value = "父级行政区划代码",position = 3)
    private String parentCode ;
    @ApiModelProperty(value = "行政区划名称",position = 4)
    private String areaName ;
    @ApiModelProperty(value = "行政级别",position = 5)
    private String areaLevel ;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }
}
