package com.iot.smart.water.meter.service;


import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.Response;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import sun.util.calendar.BaseCalendar;

import java.text.DateFormat;

import static org.mockito.Mockito.doReturn;
import static org.junit.Assert.*;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 *
 * Created by Chenziyu on 2019/6/22
 **/

@RunWith(SpringRunner.class)

public class MeterServiceTest {

    @Configuration
    static class MeterServiceConfig {
        @Bean
        public MeterService meterService() {
            return  new MeterService();
        }
    }

   @Autowired
   private MeterService meterService;

    @MockBean
    private MeterDao meterDao;



    @Test
    public void getMeters() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc("ghugjkafj87979");
        meter.setMeterName("sensor1");
        meter.setRoom("B232");
        meter.setMemberContact("343242@qq.com");
        meter.setMemberName("tete");

        Mockito.when(meterDao.selectAllMeter())
                .thenReturn(Collections.singletonList(meter));
        List<Meter> result = meterDao.selectAllMeter();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getMemberName()).isEqualTo("tete");


}

    @Test
    public void addMeter() {
    }

    @Test
    public void updateMeter() {
    }

    @Test
    public void deleteMeter() {
    }
}