package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.Impl.MeterServiceImpl;
import com.iot.smart.water.meter.service.MeterService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterServiceTest {

    @Configuration
    static class MeterServiceConfig {
        @Bean
        public MeterService meterService() {
            return new MeterServiceImpl();
        }
    }

    @MockBean
    private MeterMapper meterMapper;

    @MockBean
    private DataService dataService;

    @Autowired
    private MeterService meterService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getWaterBill() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc("ghugjkafj87979");
        meter.setMeterName("sensor1");
        meter.setRoom("B232");
        meter.setMemberContact("343242@qq.com");
        meter.setMemberName("tete");
        WaterBill waterBill = new WaterBill();
        Data data = new Data();
        waterBill.setTotalMilliters(data.getTotalMilliters());
        waterBill.setTotalMilliters(data.getTotalMilliters()/1000f*25);

        Mockito.when(meterMapper.selectAllMeter()).thenReturn(Collections.singletonList(meter));
        List result = meterService.getWaterBill();
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void setMemberVolume() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc(null);
        meter.setMeterName("tete");
        meter.setRoom("1");
        meter.setMemberContact("111111");
        meter.setVolume(100.0f);

        Mockito.when(meterMapper.selectMeterByMemberName(meter.getMemberName())).thenReturn(meter);
        boolean result = meterService.setMemberVolume(meter, meter.getVolume());
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void getMeter() {
        Meter meter = new Meter();
        meter.setMid(1);
        meter.setMeterDesc(null);
        meter.setMeterName("tete");
        meter.setRoom("1");
        meter.setMemberContact("111111");
        meter.setVolume(100.0f);

        Mockito.when(meterMapper.selectMeterByMemberName(meter.getMemberName())).thenReturn(meter);
        Meter result = meterService.getMeter(meter.getMemberName());
        Assertions.assertThat(result.getMemberName()).isEqualTo(meter.getMemberName());
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

        Mockito.when(meterMapper.selectAllMeter())
                .thenReturn(Collections.singletonList(meter));
        List<Meter> result = meterService.getMeters();
        Assertions.assertThat(result.size()).isEqualTo(1);
        Assertions.assertThat(result.get(0).getMemberName()).isEqualTo("tete");
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

        Mockito.when(meterMapper.insertMeter(meter)).thenReturn(1);
        Meter result = meterService.addMeter(meter);
        Assertions.assertThat(result).isEqualTo(meter);
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

        Mockito.when(meterMapper.selectMeterById(meter.getMid())).thenReturn(meter);
        Mockito.when(meterMapper.updateMeter(meter)).thenReturn(1);
        Meter result = meterService.updateMeter(meter);
        Assertions.assertThat(result.getMid()).isEqualTo(1);
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

        Mockito.when(meterMapper.selectMeterById(meter.getMid())).thenReturn(meter);
        Mockito.when(meterMapper.deleteMeterById(meter.getMid())).thenReturn(1);
        Meter result = meterService.deleteMeter(1);
        Assertions.assertThat(result).isEqualTo(meter);
    }
}