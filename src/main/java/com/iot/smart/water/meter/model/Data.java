package com.iot.smart.water.meter.model;

public class Data {
    private int id;
    private String sensorName;
    private float flowRate;
    private float flowMilliters;
    private float totalMilliters;
    private long readingTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public float getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(float flowRate) {
        this.flowRate = flowRate;
    }

    public float getFlowMilliters() {
        return flowMilliters;
    }

    public void setFlowMilliters(float flowMilliters) {
        this.flowMilliters = flowMilliters;
    }

    public float getTotalMilliters() {
        return totalMilliters;
    }

    public void setTotalMilliters(float totalMilliters) {
        this.totalMilliters = totalMilliters;
    }

    public long getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(long readingTime) {
        this.readingTime = readingTime;
    }
}
