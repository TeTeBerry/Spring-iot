package com.iot.smart.water.meter.model;

import java.sql.Timestamp;

public class Data {
    private int id;
    private String sensorName;
    private float flowRate;
    private float flowMilliters;
    private float totalMilliLitres;
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
        return totalMilliLitres;
    }

    public void setTotalMilliters(float totalMilliLitres) {
        this.totalMilliLitres = totalMilliLitres;
    }

    public float getTotalMilliLitres() {
        return totalMilliLitres;
    }

    public void setTotalMilliLitres(float totalMilliLitres) {
        this.totalMilliLitres = totalMilliLitres;
    }

    public Timestamp getReading_time() {
        return reading_time;
    }

    public void setReading_time(Timestamp reading_time) {
        this.reading_time = reading_time;
    }
}
