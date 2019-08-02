package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Member;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.MeterRequest;
import com.iot.smart.water.meter.model.Volume;
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

import java.util.ArrayList;
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
    private MemberMapper memberMapper;

    @MockBean
    private VolumeMapper volumeMapper;

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
        meter.setId(31);
        meter.setMeterDesc("3G&4SENSOR");
        meter.setMeterName("Sensor-1");
        meter.setMember_id(1);

        Member member = new Member();
        member.setId(1);
        member.setName("keke");
        member.setUser_id(1);

        Date date = new Date();

        String start = DateUtil.getMonthStartTimestamp(date);
        String end = DateUtil.getMonthEndTimestamp(date);


        Data data = new Data();
        data.setTotalMilliters(10000);
        WaterBill waterBill = new WaterBill();
        waterBill.setTotalMilliters(data.getTotalMilliters());
        waterBill.setFee(data.getTotalMilliters()/1000*25);


        waterBill.setMeterName(meter.getMeterName());
        waterBill.setMonth(DateUtil.getMonth(date));
        waterBill.setMemberName(member.getName());


        Mockito.when(meterMapper.selectMeterByName(meter.getMeterName())).thenReturn(meter);
        Mockito.when(memberMapper.selectMemberById(meter.getMember_id())).thenReturn(member);
        Mockito.when(dataService.getLatestData(meter.getMeterName(),start,end)).thenReturn(data);

        WaterBill result = meterService.getWaterBill(meter.getMeterName());
        Assertions.assertThat(result.toString()).isEqualTo(waterBill.toString());

    }

    @Test
    public void setMemberVolume() {
        Volume volume = new Volume();
        volume.setVolume(100);


        Mockito.when(volumeMapper.insertVolume(volume)).thenReturn(1);
        boolean result = meterService.setMemberVolume(volume, 1000);
        Assertions.assertThat(result).isEqualTo(true);
    }

//    @Test
//    public void getMeterAndMember() {
//        Meter meter = new Meter();
//        meter.setId(1);
//        meter.setMeterDesc("G3&4");
//        meter.setMeterName("tete");
//        Member member = new Member();
//        member.setName("tete");
//        member.setRoom("b123");
//        member.setContact("3423432@qq.com");
//        member.setId(1);
//        meter.setMember(member);
//
//
//        Mockito.when(meterMapper.selectMeterAndMember()).thenReturn(Collections.singletonList(meter));
//        List<Meter> result = meterService.getMeterAndMember();
//        Assertions.assertThat(result.size()).isEqualTo(1);
//    }

    @Test
    public void addMeter() {


        Member member = new Member();
        member.setId(1);
        member.setName("tete");
        member.setRoom("b123");
        member.setContact("12321@qq.com");
        member.setUser_id(1);

        Meter meter = new Meter();
        meter.setId(1);
        meter.setMeterName("sensor-1");
        meter.setMeterDesc("g3&4");



        MeterRequest meterRequest = new MeterRequest();
        meterRequest.setMember_id(meter.getId());
        meterRequest.setMeterName(meter.getMeterName());
        meterRequest.setName(member.getName());
        meterRequest.setContact(member.getContact());
        meterRequest.setUser_id(member.getUser_id());
        meterRequest.setRoom(member.getRoom());
        meterRequest.setMeter_id(meter.getId());

        Mockito.when(memberMapper.insertMember(member)).thenReturn(1);
        Mockito.when(meterMapper.insertMeter(meter)).thenReturn(1);
        MeterRequest result = meterService.addMeter(meterRequest);
        Assertions.assertThat(result).isEqualTo(meterRequest);
    }

    @Test
    public void updateMeter() {
        MeterRequest meterRequest = new MeterRequest();
        meterRequest.setMeter_id(1);
        meterRequest.setMember_id(1);
        meterRequest.setContact("sfsdfsd@qq.com");
        meterRequest.setRoom("fdsfa");
        meterRequest.setName("tete");
        meterRequest.setMeterDesc("keke");
        meterRequest.setMeterName("sensor1");

        Meter meter = new Meter();
        meter.setId(meterRequest.getMeter_id());
        meter.setMeterName(meterRequest.getMeterName());
        meter.setMeterDesc(meterRequest.getMeterDesc());

        Member member = new Member();
        member.setId(meterRequest.getMeter_id());
        member.setContact(meterRequest.getContact());
        member.setName(meterRequest.getName());
        member.setRoom(meterRequest.getRoom());


        Mockito.when(meterMapper.updateMeter(meter)).thenReturn(1);
        Mockito.when(memberMapper.updateMember(member)).thenReturn(1);
        boolean result = meterService.updateMeter(meterRequest);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void deleteMeter() {
        Meter meter = new Meter();
        meter.setId(1);

        Member member = new Member();
        member.setId(1);

        Mockito.when(volumeMapper.deleteVolumeByMeterId(meter.getId())).thenReturn(1);
        Mockito.when(meterMapper.deleteMeterById(meter.getId())).thenReturn(1);
        Mockito.when(memberMapper.deleteMemberById(member.getId())).thenReturn(1);
        boolean result = meterService.deleteMeter(1,1);
        Assertions.assertThat(result).isEqualTo(true);
    }
}