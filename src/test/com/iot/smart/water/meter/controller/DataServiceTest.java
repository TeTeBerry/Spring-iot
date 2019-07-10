package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.DailyData;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.MonthlyData;
import com.iot.smart.water.meter.model.WeeklyData;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.Impl.DataServiceImpl;
import com.iot.smart.water.meter.util.DateUtil;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javafx.util.Pair;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataServiceTest {

    @Configuration
    static class DataServiceConfig {
        @Bean
        public DataService dataService() {
            return new DataServiceImpl();
        }
    }

    @MockBean
    private DataMapper dataMapper;

    @Autowired
    private DataService dataService;

    @Test
    public void getMonthlyData() {
        Meter meter = new Meter();
        meter.setMeterName("sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<MonthlyData> result = dataService.getMonthlyData(meter.getMeterName(), "2019-07-10");
        Assertions.assertThat(result.size()).isEqualTo(31);
    }

    @Test
    public void getWeeklyData() {
        Meter meter = new Meter();
        meter.setMeterName("sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<WeeklyData> result = dataService.getWeeklyData(meter.getMeterName(), "2019-07-10");
        Assertions.assertThat(result.size()).isEqualTo(7);
    }

    @Test
    public void getDailyData() {
        Meter meter = new Meter();
        meter.setMeterName("sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<DailyData> result = dataService.getDailyData(meter.getMeterName(), "2019-07-10");
        Assertions.assertThat(result.size()).isEqualTo(24);
    }

    @Test
    public void getLatestData() {
        Date date = new Date();
        String start = DateUtil.getMonthStartTimestamp(date);
        String end = DateUtil.getMonthEndTimestamp(date);

        Meter meter = new Meter();
        meter.setMeterName("sensor1");

        Data data = new Data();
        data.setSensorName("sensor1");
        data.setTotalMilliters(100);
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        data.setReading_time(ts);

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), start, end)).thenReturn(data);
        Data result = dataService.getLatestData(meter.getMeterName(), start, end);
        Assertions.assertThat(result.getTotalMilliters()).isEqualTo(100);
    }

    @Test
    public void whetherExceedLimit() {
        Meter meter = new Meter();
        meter.setMeterName("sensor1");
        meter.setVolume(100);

        Data data = new Data();
        data.setSensorName("sensor1");
        data.setTotalMilliters(100);
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        data.setReading_time(ts);

        Mockito.when(dataMapper.selectLatestDataByName(meter.getMeterName())).thenReturn(data);
        Pair<Boolean, Boolean> result = dataService.whetherExceedLimit(meter);
        Assertions.assertThat(result.getKey()).isEqualTo(true);
        Assertions.assertThat(result.getValue()).isEqualTo(false);
    }

}
