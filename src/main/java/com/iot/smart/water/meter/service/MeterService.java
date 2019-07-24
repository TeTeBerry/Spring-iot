package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.*;

import java.util.List;

public interface MeterService {

    WaterBill getWaterBill(String meterName);

    boolean setMemberVolume(Volume volume, long newVolumeNum);

//    List<Meter> getMeters();

    MeterRequest addMeter(MeterRequest MeterRequest);

    boolean updateMeter(MeterRequest meterRequest);

    boolean deleteMeter(int mid,int bid);

    List<Meter> getMeterAndMember();

}
