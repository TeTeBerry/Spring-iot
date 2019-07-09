package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.DailyData;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.MonthlyData;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.model.WeeklyData;


import java.util.List;

public interface MeterService {

    List<MonthlyData> getMonthlyDatas(String meterName, String date);

    List<WeeklyData> getWeeklyDatas(String meterName, String date);

    List<DailyData> getDailyDatas(String meterName, String date);

    List<WaterBill> getWaterBill();

    boolean setMemberVolume(String memberName, float volume);

    List<Meter> getMeters();

    Meter addMeter(Meter meter);

    Meter updateMeter(Meter meter);

    Meter deleteMeter(int mid);
}
