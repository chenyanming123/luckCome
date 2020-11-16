package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AppDataAppointment {
    @ApiModelProperty(value = "", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "用户id", position = 2)
    private Integer userId;
    @ApiModelProperty(value = "对方id", position = 3)
    private Integer otherId;
    @ApiModelProperty(value = "状态", position = 4)
    private Integer status;
    @ApiModelProperty(value = "登记、更新时间", position = 5)
    private Date registerTime;
    @ApiModelProperty(value = "创建时间", position = 6)
    private Date createTime;
    @ApiModelProperty(value = "数据状态，0：数据删除，1：数据可用", position = 7)
    private Integer dataStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getOtherId() {
        return otherId;
    }

    public void setOtherId(Integer otherId) {
        this.otherId = otherId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getDataStatus() {
        return dataStatus;
    }

    public void setDataStatus(Integer dataStatus) {
        this.dataStatus = dataStatus;
    }
}
