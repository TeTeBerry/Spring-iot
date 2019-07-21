package com.iot.smart.water.meter.model;

import java.util.Date;

public class User {
    private Integer uid;
    private String userName;
    private String password;
    private Date createDate;
    private String roles;


    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRoles() { return roles;}

    public void setRoles(String roles) {
        this.roles = roles;
    }

}
