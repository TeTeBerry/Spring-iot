package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
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

import java.util.Date;

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

//    @Test
//    public void getLatestData() {
//        Date date = new Date();
//        long start = DateUtil.getMonthStartTimestamp(date);
//        long end = DateUtil.getMonthEndTimestamp(date);
//
//        Meter meter = new Meter();
//        meter.setMeterName("sensor1");
//
//        Data data = new Data();
//        data.setSensorName("sensor1");
//        data.setTotalMilliters(100);
//        data.setReadingTime(System.currentTimeMillis());
//
//        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(), start, end)).thenReturn(data);
//        Data result = dataService.getLatestData(meter.getMeterName(), start, end);
//        Assertions.assertThat(result.getTotalMilliters()).isEqualTo(100);
//    }
//
//    @Test
//    public void whetherExceedLimit() {
//        Meter meter = new Meter();
//        meter.setMeterName("sensor1");
//        meter.setVolume(100);
//
//        Data data = new Data();
//        data.setSensorName("sensor1");
//        data.setTotalMilliters(100);
//        data.setReadingTime(System.currentTimeMillis());
//
//        Mockito.when(dataMapper.selectLatestDataByName(meter.getMeterName())).thenReturn(data);
//        Pair<Boolean, Boolean> result = dataService.whetherExceedLimit(meter);
//        Assertions.assertThat(result.getKey()).isEqualTo(true);
//        Assertions.assertThat(result.getValue()).isEqualTo(false);
//    }
}
