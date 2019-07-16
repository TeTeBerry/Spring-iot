package com.iot.smart.water.meter.model;

public class MonthlyData {
    private String day;
    private long totalMilliters;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(long totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
