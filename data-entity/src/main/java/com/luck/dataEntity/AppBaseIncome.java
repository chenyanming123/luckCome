package com.luck.dataEntity;

import io.swagger.annotations.ApiModelProperty;

public class AppBaseIncome {
    @ApiModelProperty(value = "id", position = 1)
    private Integer id ;
    @ApiModelProperty(value = "收入范围", position = 2)
    private String income;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }
}
