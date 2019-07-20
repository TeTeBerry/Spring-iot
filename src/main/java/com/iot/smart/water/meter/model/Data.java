package com.iot.smart.water.meter.model;

import java.sql.Timestamp;

public class Data {
    private int id;
    private String sensorName;
    private int flowRate;
    private long flowMilliters;
    private long totalMilliLitres;
    private Timestamp reading_time;

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

    public int getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(int flowRate) {
        this.flowRate = flowRate;
    }

    public long getFlowMilliters() {
        return flowMilliters;
    }

    public void setFlowMilliters(long flowMilliters) {
        this.flowMilliters = flowMilliters;
    }

    public long getTotalMilliters() {
        return totalMilliLitres;
    }

    public void setTotalMilliters(long totalMilliLitres) {
        this.totalMilliLitres = totalMilliLitres;
    }

    public Timestamp getReading_time() {
        return reading_time;
    }

    public void setReading_time(Timestamp reading_time) {
        this.reading_time = reading_time;
    }
}
