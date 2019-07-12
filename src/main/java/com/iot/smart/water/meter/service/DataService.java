package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.*;

import org.springframework.stereotype.Service;

import java.util.List;
import javafx.util.Pair;


@Service
public interface DataService {


    Pair<Boolean, Boolean> whetherExceedLimit(Meter meter);

    Data getLatestData(String meterName, String start, String end);

    List<DailyData> getDailyData(String meterName, String date);

    List<MonthlyData> getMonthlyData(String meterName, String date);

    List<WeeklyData> getWeeklyData(String meterName, String date);

    boolean notifyMe(String message);



}
