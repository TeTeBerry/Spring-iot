package com.iot.smart.water.meter.model;

import java.util.Date;

public class Meter {
    private int mid;
    private String meterName;
    private String meterDesc;
    private String memberName;
    private String room;
    private String memberContact;
    private Date createDate;
    private float volume;
    private int changeVolumeLimit;
    private int notifyLimit;

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public String getMeterName() {
        return meterName;
    }

    public void setMeterName(String meterName) {
        this.meterName = meterName;
    }

    public String getMeterDesc() {
        return meterDesc;
    }

    public void setMeterDesc(String meterDesc) {
        this.meterDesc = meterDesc;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMemberContact() {
        return memberContact;
    }

    public void setMemberContact(String memberContact) {
        this.memberContact = memberContact;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getChangeVolumeLimit() {
        return changeVolumeLimit;
    }

    public void setChangeVolumeLimit(int changeVolumeLimit) {
        this.changeVolumeLimit = changeVolumeLimit;
    }

    public int getNotifyLimit() {
        return notifyLimit;
    }

    public void setNotifyLimit(int notifyLimit) {
        this.notifyLimit = notifyLimit;
    }
}
