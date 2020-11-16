package com.luck.dataEntity;


import java.io.Serializable;

/**
 * 对Token进行操作的接口
 */
public class TokenUser implements Serializable {
    private Integer userId;
    private String username;
    private String lastIp;
    private String token;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLastIp() {
        return lastIp;
    }

    public void setLastIp(String lastIp) {
        this.lastIp = lastIp;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
