package com.iot.smart.water.meter.model;

import java.util.Date;


public class Meter {
    private Integer id;
    private String meterName;
    private String meterDesc;
    private Date createDate;
    private Integer member_id;



    public Integer getId() {
        return id;
    }

    public Integer getMember_id() {return  member_id;}

    public void setMember_id(Integer member_id){
        this.member_id = member_id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}
