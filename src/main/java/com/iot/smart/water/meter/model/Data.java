package com.iot.smart.water.meter.model;

import java.sql.Timestamp;

public class Data {
    private int id;
    private String sensorName;
    private String flowRate;
    private String flowMilliters;
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

    public String getFlowRate() {
        return flowRate;
    }

    public void setFlowRate(String flowRate) {
        this.flowRate = flowRate;
    }

    public String getFlowMilliters() {
        return flowMilliters;
    }

    public void setFlowMilliters(String flowMilliters) {
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
