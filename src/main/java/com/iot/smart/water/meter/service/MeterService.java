package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.WaterBill;
import java.util.List;

public interface MeterService {

    WaterBill getWaterBill(String meterName);

    boolean setMemberVolume(Meter meter, long volume);

    Meter getMeterByName(String memberName);

    List<Meter> getMeters();

    Meter addMeter(Meter meter);

    Meter updateMeter(Meter meter);

    Meter deleteMeter(int mid);
}
