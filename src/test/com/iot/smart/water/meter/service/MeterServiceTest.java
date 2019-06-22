package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.Response;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterServiceTest {

    @MockBean
    private MeterDao meterDao;

    @InjectMocks
    private MeterService meterService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

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

        Response response = meterService.getMeters();
        List<Meter> list = (List<Meter>) response.getData();
        Assertions.assertThat(list.size()).isEqualTo(1);
        Assertions.assertThat(list.get(0).getMemberName()).isEqualTo("tete");
    }

    @Test
    public void addMeter() {
        Meter meter = new Meter();
        meter.setMeterDesc("keke");
        meter.setMeterName("sensor1");
        meter.setRoom("B232");
        meter.setMemberContact("test@qq.com");
        meter.setMemberName("tete");
        meter.setCreateDate(new Date());

        Mockito.when(meterDao.insertMeter(meter)).thenReturn(1);
        Response response = meterService.addMeter(meter);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

    @Test
    public void updateMeter() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc("keke");
        meter.setMeterName("sensor1");
        meter.setRoom("B232");
        meter.setMemberContact("test@qq.com");
        meter.setMemberName("tete");

        Mockito.when(meterDao.updateMeter(meter)).thenReturn(1);
        Response response = meterService.updateMeter(meter);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

    @Test
    public void deleteMeter() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc("keke");
        meter.setMeterName("sensor1");
        meter.setRoom("B232");
        meter.setMemberContact("test@qq.com");
        meter.setMemberName("tete");

        Mockito.when(meterDao.selectMeter(meter.getMid())).thenReturn(meter);
        Mockito.when(meterDao.deleteMeter(meter.getMid())).thenReturn(1);
        Response response = meterService.deleteMeter(meter.getMid());
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

}