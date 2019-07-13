package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.util.DateUtil;
import com.iot.smart.water.meter.util.WeekUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;


@Service
public class DataServiceImpl implements DataService {

    final String USER_TOKEN = "pCtUdqqKEqGzZEVJDRUWJIs3ZZgCdo3joYcH9FeTLQ2";

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);


    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private MeterMapper meterMapper;


    @Override
    public Data getLatestData(String meterName, String start, String end) {
        return dataMapper.selectLatestDataInMonthByName(meterName, start, end);
    }


    @Override
    public List<DailyData> getDailyData(String meterName, String date) {
        Date parseDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        if (parseDate == null) {
            return null;
        }
        List<DailyData> list = new ArrayList<>();
        for (int i = 0; i < 24; i++) {

            DailyData dailyData = new DailyData();
            String start;
            String end;
            if (i < 10) {
                dailyData.setHour(" 0" + i + ":00");
                start = date + " 0" + i + ":00:00";
                end = date + " 0" + i + ":59:59";
            } else {
                dailyData.setHour(" " + i + ":00");
                start = date + " " + i + ":00:00";
                end = date + " " + i + ":59:59";
            }
            Data data = getLatestData(meterName, start, end);
            dailyData.setTotalMilliters(data == null ? 0 : data.getTotalMilliters());
            list.add(dailyData);
        }
        return list;
    }

    @Override
    public List<MonthlyData> getMonthlyData(String meterName, String date) {
        Date parseDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        if (parseDate == null) {
            return null;
        }
        List<MonthlyData> list = new ArrayList<>();
        int day = DateUtil.getDay(parseDate);
        int maxDay = DateUtil.getDaysOfMonth(parseDate);
        String start;
        String end;
        for (int i = 1; i <= maxDay; i++) {
            MonthlyData monthlyData = new MonthlyData();
            if (i < 10) {
                monthlyData.setDay("0" + i);
            } else {
                monthlyData.setDay(String.valueOf(i));
            }
            String dayDate = DateUtil.formatDiffDate(parseDate, i - day, "yyyy-MM-dd");
            start = dayDate + " 00:00:00";
            end = dayDate + " 23:59:59";
            Data data = getLatestData(meterName, start, end);
            monthlyData.setTotalMilliters(data == null ? 0 : data.getTotalMilliters());
            list.add(monthlyData);
        }
        return list;
    }

    @Override
    public List<WeeklyData> getWeeklyData(String meterName, String date) {
        Date parseDate = DateUtil.parseDate(date, "yyyy-MM-dd");
        if (parseDate == null) {
            return null;
        }
        List<WeeklyData> list = new ArrayList<>();
        int week = DateUtil.getWeekDay(parseDate);
        String start;
        String end;
        for (int i = 1; i <= 7; i++) {
            WeeklyData weeklyData = new WeeklyData();
            weeklyData.setWeek(WeekUtil.WEEK_CONTENT[i]);
            String weekDate = DateUtil.formatDiffDate(parseDate, i - week, "yyyy-MM-dd");
            start = weekDate + " 00:00:00";
            end = weekDate + " 23:59:59";
            Data data = getLatestData(meterName, start, end);
            weeklyData.setTotalMilliters(data == null ? 0 : data.getTotalMilliters());
            list.add(weeklyData);
        }
        return list;
    }


    private void resetCheck(Meter meter, boolean firstDayOfMonth) {
        boolean update = false;
        if (meter.getDailyCheck() == 1) {
            meter.setDailyCheck(0);
            update = true;
        }
        if (firstDayOfMonth && meter.getMonthlyCheck() == 1) {
            meter.setMonthlyCheck(0);
            update = true;
        }
        if (update) {
            meterMapper.updateMeter(meter);
        }
    }

    @Override
    public boolean notifyMe(String message) {
        LineNotify ln = new LineNotify(USER_TOKEN);
        LineParameter lineParameter = new LineParameter(message);
        try {
            ln.notifyMe(lineParameter.getMessage(),2,1);
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return true;
    }




//    @Override
//    public Pair<Boolean, Boolean> whetherExceedLimit(Meter meter) {
//        Data data = dataMapper.selectLatestDataByName(meter.getMeterName());
//        Date date = new Date();
//        long time = date.getTime();
//        Timestamp ts = new Timestamp(time);
//        if (data != null && DateUtil.isSameDay(data.getReading_time(), ts)) {
//            return new Pair<>(data.getTotalMilliters() >= meter.getVolume(),
//                    data.getTotalMilliters() >= meter.getVolume() * DateUtil.getDaysOfMonth(new Date()));
//        }
//        return new Pair<>(false, false);
//    }
//
//    @Scheduled(cron = "0 * * * * ?")
//    private void scheduleTask() {
//        List<Meter> meters = meterMapper.selectAllMeter();
//        if (meters != null) {
//            for (Meter meter : meters) {
//                    checkLimit(meter);
//                }
//            }
//    }


//    private void checkLimit(Meter meter) {
//        Pair<Boolean, Boolean> result = whetherExceedLimit(meter);
//        boolean update = false;
//        LineNotify ln = new LineNotify(USER_TOKEN);
//            if (result.getValue()) {
//                try {
//                    ln.notifyMe("This Week water exceeds the limit");
//                    meter.setweeklyCheck(1);
//                    update = true;
//                } catch (IOException ex) {
//                    System.err.println(ex);
//
//                }
//                if (update) {
//                    meterMapper.updateMeter(meter);
//                }
//            }
        }


