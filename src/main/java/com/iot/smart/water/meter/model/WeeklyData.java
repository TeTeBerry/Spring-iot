package com.iot.smart.water.meter.model;

public class WeeklyData {
    private String week;
    private float totalMilliters;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(float totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
