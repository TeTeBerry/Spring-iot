package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.Impl.DataServiceImpl;
import com.iot.smart.water.meter.util.DateUtil;


import com.iot.smart.water.meter.util.line.LineNotify;
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

    @MockBean
    private MeterMapper meterMapper;

    @MockBean
    private VolumeMapper volumeMapper;

    @MockBean
    private LineNotify lineNotify;

    @Autowired
    private DataService dataService;

    @Test
    public void getMonthlyData() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<MonthlyData> result = dataService.getMonthlyData(meter.getMeterName(), "2019-07-01");
        Assertions.assertThat(result.size()).isEqualTo(31);
    }

    @Test
    public void getWeeklyData() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<WeeklyData> result = dataService.getWeeklyData(meter.getMeterName(), "2019-07-01");
        Assertions.assertThat(result.size()).isEqualTo(7);
    }

    @Test
    public void getDailyData() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor1");

        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), "", "")).thenReturn(null);
        List<DailyData> result = dataService.getDailyData(meter.getMeterName(), "2019-07-01");
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


        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), start, end)).thenReturn(data);
        Data result = dataService.getLatestData(meter.getMeterName(), start, end);
        Assertions.assertThat(result.getTotalMilliters()).isEqualTo(100);
    }
}
