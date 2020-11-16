package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AppBaseShop {
    @ApiModelProperty(value = "", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "商店名称", position = 2)
    private Integer shopName;
    @ApiModelProperty(value = "商店类型", position = 3)
    private Integer shopType;
    @ApiModelProperty(value = "商店电话", position = 4)
    private Integer shop_phone;
    @ApiModelProperty(value = "行政区", position = 5)
    private Integer area_name;
    @ApiModelProperty(value = "经度", position = 6)
    private Date lon;
    @ApiModelProperty(value = "纬度", position = 7)
    private Date lat;
    @ApiModelProperty(value = "商店地址", position = 8)
    private Date shop_address;
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

    public Integer getShopName() {
        return shopName;
    }

    public void setShopName(Integer shopName) {
        this.shopName = shopName;
    }

    public Integer getShopType() {
        return shopType;
    }

    public void setShopType(Integer shopType) {
        this.shopType = shopType;
    }

    public Integer getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(Integer shop_phone) {
        this.shop_phone = shop_phone;
    }

    public Integer getArea_name() {
        return area_name;
    }

    public void setArea_name(Integer area_name) {
        this.area_name = area_name;
    }

    public Date getLon() {
        return lon;
    }

    public void setLon(Date lon) {
        this.lon = lon;
    }

    public Date getLat() {
        return lat;
    }

    public void setLat(Date lat) {
        this.lat = lat;
    }

    public Date getShop_address() {
        return shop_address;
    }

    public void setShop_address(Date shop_address) {
        this.shop_address = shop_address;
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
