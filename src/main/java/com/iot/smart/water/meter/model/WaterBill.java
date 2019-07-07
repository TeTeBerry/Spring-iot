package com.iot.smart.water.meter.model;

public class WaterBill {
    private int month;
    private float fee;
    private String meterName;
    private String memberName;
    private float totalMilliters;

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(float totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
