package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class AppDataImages {
    @ApiModelProperty(value = "", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "用户id", position = 2)
    private Integer userId;
    @ApiModelProperty(value = "图片路径", position = 3)
    private String imagePath;
    @ApiModelProperty(value = "图片名称", position = 4)
    private String imageName;
    @ApiModelProperty(value = "登记、更新时间", position = 5)
    private Date registerTime;
    @ApiModelProperty(value = "创建时间", position = 6)
    private Date createTime;

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
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
