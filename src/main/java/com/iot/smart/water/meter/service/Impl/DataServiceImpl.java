package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.util.DateUtil;
import com.iot.smart.water.meter.util.line.LineNotify;
import com.iot.smart.water.meter.util.WeekUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.scheduling.annotation.Scheduled;

@Service
@EnableScheduling
public class DataServiceImpl implements DataService {

    private static final Logger logger = LoggerFactory.getLogger(DataServiceImpl.class);

    @Autowired
    private DataMapper dataMapper;

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private LineNotify lineNotify;

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

    @Scheduled(cron = "0 * * * * ?")
    private void scheduleTask() {
        logger.info("DataServiceImpl schedule task");
        List<Meter> meters = meterMapper.selectAllMeter();
        if (meters != null) {
            for (Meter meter : meters) {
                if (meter.getNotifyLimit() == 0) {
                    Data data = dataMapper.selectLatestDataByName(meter.getMeterName());
                    if (meter.getVolume() > 0 && data.getTotalMilliters() >= meter.getVolume()) {
                        meter.setNotifyLimit(1);
                        meterMapper.updateMeter(meter);
                        lineNotify.notifyMe("This Week water exceeds the limit", 16, 1);
                    }
                }
            }
        }
    }
}


