package com.iot.smart.water.meter.model;

public class WeeklyData {
    private String week;
    private long totalMilliters;

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public long getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(long totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
