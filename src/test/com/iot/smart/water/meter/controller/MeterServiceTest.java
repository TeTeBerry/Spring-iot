package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.Impl.MeterServiceImpl;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;
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
    private DataMapper dataMapper;

    @MockBean
    private DataService dataService;

    @Autowired
    private MeterService meterService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }


//    @Test
//    public void getWaterBill() {
//        Meter meter = new Meter();
//        meter.setId(31);
//        meter.setMeterDesc("3G&4SENSOR");
//        meter.setMeterName("Sensor-1");
//        Date date = new Date();
//        Data data = new Data();
//        data.setTotalMilliters(10000);
//        WaterBill waterBill = new WaterBill();
//        waterBill.setTotalMilliters(data.getTotalMilliters());
//        waterBill.setFee(data.getTotalMilliters()/1000*25);
//
//        waterBill.setMonth(DateUtil.getMonth(date));
//        waterBill.setMeterName(meter.getMeterName());
//        waterBill.setMemberName(meter.getMemberName());
//
//        Mockito.when(meterMapper.selectAllMeter()).thenReturn(Collections.singletonList(meter));
//        Mockito.when(dataMapper.selectLatestDataInMonthByName(meter.getMeterName(),"2019-07-01","2019-07-18")).thenReturn(null);
//
//        WaterBill result = meterService.getWaterBill(meter.getMeterName());
//        Assertions.assertThat(result.getMemberName()).isEqualTo("tete");
//    }
//
//    @Test
//    public void setMemberVolume() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("G3&4");
//        meter.setMeterName("tete");
//        meter.setRoom("1");
//        meter.setMemberContact("111111");
//        meter.setVolume(100);
//
//        Mockito.when(meterMapper.selectMeterByMemberName(meter.getMemberName())).thenReturn(meter);
//        boolean result = meterService.setMemberVolume(meter, meter.getVolume());
//        Assertions.assertThat(result).isEqualTo(true);
//    }
//
//    @Test
//    public void getMeterByName() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("G3&4");
//        meter.setMeterName("tete");
//        meter.setRoom("1");
//        meter.setMemberContact("111111");
//
//
//        Mockito.when(meterMapper.selectMeterByMemberName(meter.getMemberName())).thenReturn(meter);
//        Meter result = meterService.getMeterByName(meter.getMemberName());
//        Assertions.assertThat(result.getMemberName()).isEqualTo(meter.getMemberName());
//    }
//
//    @Test
//    public void getMeters() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("ghugjkafj87979");
//        meter.setMeterName("sensor1");
//        meter.setRoom("B232");
//        meter.setMemberContact("343242@qq.com");
//        meter.setMemberName("tete");
//
//        Mockito.when(meterMapper.selectAllMeter())
//                .thenReturn(Collections.singletonList(meter));
//        List<Meter> result = meterService.getMeters();
//        Assertions.assertThat(result.size()).isEqualTo(1);
//        Assertions.assertThat(result.get(0).getMemberName()).isEqualTo("tete");
//    }
//
//    @Test
//    public void addMeter() {
//        Meter meter = new Meter();
//        meter.setMeterDesc("keke");
//        meter.setMeterName("sensor1");
//        meter.setRoom("B232");
//        meter.setMemberContact("test@qq.com");
//        meter.setMemberName("tete");
//        meter.setCreateDate(new Date());
//
//        Mockito.when(meterMapper.insertMeter(meter)).thenReturn(1);
//        Meter result = meterService.addMeter(meter);
//        Assertions.assertThat(result).isEqualTo(meter);
//    }
//
//    @Test
//    public void updateMeter() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("keke");
//        meter.setMeterName("sensor1");
//        meter.setRoom("B232");
//        meter.setMemberContact("test@qq.com");
//        meter.setMemberName("tete");
//
//        Mockito.when(meterMapper.selectMeterById(meter.getId())).thenReturn(meter);
//        Mockito.when(meterMapper.updateMeter(meter)).thenReturn(1);
//        Meter result = meterService.updateMeter(meter);
//        Assertions.assertThat(result.getId()).isEqualTo(1);
//    }
//
//    @Test
//    public void deleteMeter() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("keke");
//        meter.setMeterName("sensor1");
//        meter.setRoom("B232");
//        meter.setMemberContact("test@qq.com");
//        meter.setMemberName("tete");
//
//        Mockito.when(meterMapper.selectMeterById(meter.getId())).thenReturn(meter);
//        Mockito.when(meterMapper.deleteMeterById(meter.getId())).thenReturn(1);
//        Meter result = meterService.deleteMeter(1);
//        Assertions.assertThat(result).isEqualTo(meter);
//    }
}