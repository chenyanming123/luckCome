package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class SysUser {
    @ApiModelProperty(value = "", position = 1)
    private Integer id;
    @ApiModelProperty(value = "真实姓名", position = 2)
    private String realName;
    @ApiModelProperty(value = "账户", position = 3)
    private String userName;
    @ApiModelProperty(value = "密码", position = 4)
    private String password;
    @ApiModelProperty(value = "电话", position = 5)
    private String mobile;
    @ApiModelProperty(value = "角色列表", position = 6)
    private String roles;
    @ApiModelProperty(value = "登记、更新时间", position = 7)
    private Date registerTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }
}
