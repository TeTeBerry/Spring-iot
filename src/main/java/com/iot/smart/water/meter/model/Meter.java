package com.iot.smart.water.meter.model;

public class Meter {
    private String mid;
    private String meterName;
    private String meterDesc;
    private String createDate;

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
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

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
