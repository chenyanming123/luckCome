package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AppUser {
    @ApiModelProperty(value = "", position = 1)
    private Integer id;
    @ApiModelProperty(value = "昵称", position = 2)
    private String nickName;
    @ApiModelProperty(value = "性别。0：未知；1：男；2：女", position = 3)
    private Integer gender;
    @ApiModelProperty(value = "电话", position = 4)
    private String mobile;
    @ApiModelProperty(value = "国家", position = 5)
    private String country;
    @ApiModelProperty(value = "省", position = 6)
    private String province;
    @ApiModelProperty(value = "市", position = 7)
    private String city;
    @ApiModelProperty(value = "微信头像路径", position = 7)
    private String avatarUrlWx;
    @ApiModelProperty(value = "抖音头像路径", position = 7)
    private String avatarUrlDy;
    @ApiModelProperty(value = "语言", position = 8)
    private String language;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAvatarUrlWx() {
        return avatarUrlWx;
    }

    public void setAvatarUrlWx(String avatarUrlWx) {
        this.avatarUrlWx = avatarUrlWx;
    }

    public String getAvatarUrlDy() {
        return avatarUrlDy;
    }

    public void setAvatarUrlDy(String avatarUrlDy) {
        this.avatarUrlDy = avatarUrlDy;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
