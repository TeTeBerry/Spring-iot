package com.iot.smart.water.meter.controller;


import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;


public class MeterControllerUnitTest {

    @Mock
    MeterService meterService;

    @Mock
    MeterMapper meterMapper;

    @Mock
    MemberMapper memberMapper;

    @Mock
    DataService dataService;

    @Mock
    VolumeMapper volumeMapper;

    @InjectMocks
    MeterController meterController;

    @Before
    public void init() {
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

        Data data = new Data();
        data.setTotalMilliters(10000);
        WaterBill waterBill = new WaterBill();
        waterBill.setTotalMilliters(data.getTotalMilliters());
        waterBill.setFee(data.getTotalMilliters()/1000*25);

        waterBill.setMonth(DateUtil.getMonth(new Date()));
        waterBill.setMeterName(meter.getMeterName());
        waterBill.setMemberName(member.getName());

        Mockito.when(meterMapper.selectMeterByName(meter.getMeterName())).thenReturn(meter);
        Mockito.when(memberMapper.selectMemberById(meter.getMember_id())).thenReturn(member);
        Mockito.when(dataService.getLatestData(meter.getMeterName(),"2019-07-01","2019-07-18")).thenReturn(data);

        Response result = meterController.getWaterBill(meter.getMeterName());
        Assertions.assertThat(result.getCode()).isEqualTo(200);

    }
//
//    @Test
//    public void setVolume() {
//        Volume volume = new Volume();
//        volume.setMember_id(1);
//        volume.setMeter_id(1);
//        volume.setChangeLimit(0);
//        volume.setVolume(1000);
//
//
//
//        String token = "2f0e75d660199693153312156583df13";
//
//        Mockito.when(volumeMapper.insertVolume(volume)).thenReturn(1);
//        Response result = meterController.setVolume(token,volume.getMember_id(),volume.getMeter_id(),volume.getVolume());
//        Assertions.assertThat(result).isEqualTo(true);
//    }


    @Test
    public void getMeterAndMember() {
        Meter meter = new Meter();
        meter.setId(1);
        meter.setMeterDesc("G3&4");
        meter.setMeterName("tete");

        Member member = new Member();
        member.setId(1);
        member.setContact("afadfa@qq.com");
        member.setName("keke");
        member.setRoom("b123");


        Mockito.when(meterMapper.selectMeterAndMember()).thenReturn(Collections.singletonList(meter));
        Response result = meterController.getMeterAndMember();
        Assertions.assertThat(result.getCode()).isEqualTo(200);


    }

    @Test
    public void updateMeter() {
    }

    @Test
    public void deleteMeter() {
        Meter meter = new Meter();
        meter.setId(1);
        meter.setMeterDesc("keke");
        meter.setMeterName("sensor1");
        Member member = new Member();
        member.setId(1);
        String token = "2f0e75d660199693153312156583df13";

        Mockito.when(volumeMapper.deleteVolumeByMeterId(meter.getId())).thenReturn(1);
        Mockito.when(meterMapper.deleteMeterById(meter.getId())).thenReturn(1);
        Mockito.when(memberMapper.deleteMemberById(member.getId())).thenReturn(1);
        Response result = meterController.deleteMeter(token,1,1);
        Assertions.assertThat(result).isEqualTo(true);
    }

    @Test
    public void addMeter() {
    }
}