package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;
import org.beetl.sql.core.annotatoin.AssignID;

import java.util.Date;

public class AppDataUserinfo {
    @ApiModelProperty(value = "", position = 1)
    @AssignID
    private Integer userId;
    @ApiModelProperty(value = "昵称", position = 2)
    private String nickName;
    @ApiModelProperty(value = "微信号", position = 3)
    private String wechat;
    @ApiModelProperty(value = "性别。0：未知；1：男；2：女", position = 4)
    private Integer sex;
    @ApiModelProperty(value = "出生日期", position = 5)
    private Date birthYear;
    @ApiModelProperty(value = "居住地", position = 6)
    private String address;
    @ApiModelProperty(value = "户籍地", position = 7)
    private String domicile;
    @ApiModelProperty(value = "学历", position = 8)
    private Integer education;
    @ApiModelProperty(value = "身高", position = 9)
    private Integer stature;
    @ApiModelProperty(value = "工作", position = 10)
    private String work;
    @ApiModelProperty(value = "收入", position = 11)
    private Integer income;
    @ApiModelProperty(value = "个人描述", position = 13)
    private String description;
    @ApiModelProperty(value = "择偶要求", position = 13)
    private String chooseRequire;
    @ApiModelProperty(value = "登记、更新时间", position = 14)
    private Date registerTime;
    @ApiModelProperty(value = "创建时间", position = 15)
    private Date createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Date birthYear) {
        this.birthYear = birthYear;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public Integer getEducation() {
        return education;
    }

    public void setEducation(Integer education) {
        this.education = education;
    }

    public Integer getStature() {
        return stature;
    }

    public void setStature(Integer stature) {
        this.stature = stature;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getChooseRequire() {
        return chooseRequire;
    }

    public void setChooseRequire(String chooseRequire) {
        this.chooseRequire = chooseRequire;
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
