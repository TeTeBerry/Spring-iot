package com.iot.smart.water.meter.model;

import java.util.Date;

public class Member {
    private Integer id;
    private String name;
    private String room;
    private String contact;
    private Date createDate;
    private Integer user_id;
    private Meter meter;

    public Meter getMeter(){return  meter;}

    public void setMeter(Meter meter){ this.meter = meter;}


    public Integer getUser_id() {return user_id;}

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
