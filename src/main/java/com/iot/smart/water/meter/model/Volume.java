package com.iot.smart.water.meter.model;

public class Volume {
    private Integer id;
    private Integer member_id;
    private Integer meter_id;
    private long volume;
    private int changeLimit;
    private int notifyLimit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMember_id() {
        return member_id;
    }

    public void setMember_id(Integer member_id) {
        this.member_id = member_id;
    }

    public Integer getMeter_id() {
        return meter_id;
    }

    public void setMeter_id(Integer meter_id) {
        this.meter_id = meter_id;
    }

    public long getVolume() {
        return volume;
    }

    public void setVolume(long volume) {
        this.volume = volume;
    }

    public int getChangeLimit() {
        return changeLimit;
    }

    public void setChangeLimit(int changeLimit) {
        this.changeLimit = changeLimit;
    }

    public int getNotifyLimit() {
        return notifyLimit;
    }

    public void setNotifyLimit(int notifyLimit) {
        this.notifyLimit = notifyLimit;
    }
}
