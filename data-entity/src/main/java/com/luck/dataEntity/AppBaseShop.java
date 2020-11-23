package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AppBaseShop {
    @ApiModelProperty(value = "", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "商店名称", position = 2)
    private String shopName;
    @ApiModelProperty(value = "商店类型", position = 3)
    private String shopType;
    @ApiModelProperty(value = "商店电话", position = 4)
    private String shopPhone;
    @ApiModelProperty(value = "行政区", position = 5)
    private String areaName;
    @ApiModelProperty(value = "经度", position = 6)
    private String lon;
    @ApiModelProperty(value = "纬度", position = 7)
    private String lat;
    @ApiModelProperty(value = "商店地址", position = 8)
    private String shopAddress;
    @ApiModelProperty(value = "登记、更新时间", position = 9)
    private Date registerTime;
    @ApiModelProperty(value = "创建时间", position = 10)
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
