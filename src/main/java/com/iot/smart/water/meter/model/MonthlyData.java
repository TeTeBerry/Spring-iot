package com.iot.smart.water.meter.model;

public class MonthlyData {
    private String day;
    private float totalMilliters;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(float totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
