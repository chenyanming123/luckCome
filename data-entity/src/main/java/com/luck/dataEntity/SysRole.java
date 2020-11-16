package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

public class SysRole {
    @ApiModelProperty(value = "", position = 1)
    private Integer id;
    @ApiModelProperty(value = "角色排序", position = 2)
    private Integer serialNumber;
    @ApiModelProperty(value = "角色中文名称", position = 3)
    private String chineseName;
    @ApiModelProperty(value = "角色名称（英文）", position = 4)
    private String name;
    @ApiModelProperty(value = "权限列表", position = 5)
    private String permissions;
    @ApiModelProperty(value = "备注", position = 6)
    private String description;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
