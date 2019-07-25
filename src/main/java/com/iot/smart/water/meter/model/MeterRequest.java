package com.iot.smart.water.meter.model;

public class MeterRequest {
    private String meterName;
    private String meterDesc;
    private String name;
    private String room;
    private String contact;
    private Integer user_id;
    private Integer member_id;
    private Integer meter_id;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMeter_id() {return meter_id;}

    public void setMeter_id(Integer meter_id){this.meter_id = meter_id;}


    public Integer getMember_id() {return  member_id;}

    public void setMember_id(Integer member_id){
        this.member_id = member_id;
    }

    public Integer getUser_id() {return user_id;}

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getMeterDesc() {
        return meterDesc;
    }

    public void setMeterDesc(String meterDesc) {
        this.meterDesc = meterDesc;
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
}
