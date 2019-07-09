package com.iot.smart.water.meter.model;

public class DailyData {
    private String hour;
    private float totalMilliters;

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(float totalMilliters) {
        this.totalMilliters = totalMilliters;
    }
}
