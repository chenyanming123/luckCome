package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

public class AppBaseEducation {
    @ApiModelProperty(value = "id", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "学历", position = 2)
    private String education;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
