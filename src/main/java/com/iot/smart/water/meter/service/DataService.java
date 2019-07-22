package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.*;


import java.util.List;



public interface DataService {

    Data getLatestData(String meterName, String start, String end);

    List<DailyData> getDailyData(String meterName, String date);

    List<MonthlyData> getMonthlyData(String meterName, String date);

    List<WeeklyData> getWeeklyData(String meterName, String date);
}
