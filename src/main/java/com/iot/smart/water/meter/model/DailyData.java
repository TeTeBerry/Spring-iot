package com.iot.smart.water.meter.model;

public class DailyData {
    private String hour;
    private long totalMilliters;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public long getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(long totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
