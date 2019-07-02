package com.iot.smart.water.meter.model;

public class Data {
    private int id;
    private String sensorName;
    private float flowRate;
    private float flowMilliLtres;
    private float totalMilliLtres;
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

    public float getFlowMilliLtres() {
        return flowMilliLtres;
    }

    public void setFlowMilliLtres(float flowMilliLtres) {
        this.flowMilliLtres = flowMilliLtres;
    }

    public float getTotalMilliLtres() {
        return totalMilliLtres;
    }

    public void setTotalMilliLtres(float totalMilliLtres) {
        this.totalMilliLtres = totalMilliLtres;
    }

    public long getReadingTime() {
        return readingTime;
    }

    public void setReadingTime(long readingTime) {
        this.readingTime = readingTime;
    }
}
