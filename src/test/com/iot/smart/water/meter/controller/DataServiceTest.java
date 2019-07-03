package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.Impl.DataServiceImpl;

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
    public void whetherExceedLimit() {
        Meter meter = new Meter();
        meter.setMeterName("sensor1");
        meter.setVolume(100);

        Data data = new Data();
        data.setSensorName("sensor1");
        data.setTotalMilliLtres(100);
        data.setReadingTime(System.currentTimeMillis());

        Mockito.when(dataMapper.selectLatestDataByName(meter.getMeterName())).thenReturn(data);
        Pair<Boolean, Boolean> result = dataService.whetherExceedLimit(meter);
        Assertions.assertThat(result.getKey()).isEqualTo(true);
        Assertions.assertThat(result.getValue()).isEqualTo(false);
    }
}
