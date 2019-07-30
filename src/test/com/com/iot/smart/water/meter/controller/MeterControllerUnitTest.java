package com.iot.smart.water.meter.controller;


import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.dao.VolumeMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;
import com.iot.smart.water.meter.util.RoleManager;
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
    private MeterService meterService;

    @Mock
    private MeterMapper meterMapper;

    @Mock
    private MemberMapper memberMapper;


    @Mock
    private VolumeMapper volumeMapper;

    @Mock
    private RoleManager roleManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    MeterController meterController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getWaterBill() {
        Meter meter = new Meter();
        meter.setMeterName("Sensor-1");

        Member member = new Member();
        member.setName("keke");

        Data data = new Data();
        data.setTotalMilliters(10000);
        WaterBill waterBill = new WaterBill();
        waterBill.setTotalMilliters(data.getTotalMilliters());
        waterBill.setFee(data.getTotalMilliters()/1000*25);

        waterBill.setMonth(DateUtil.getMonth(new Date()));
        waterBill.setMeterName(meter.getMeterName());
        waterBill.setMemberName(member.getName());

        Mockito.when(meterService.getWaterBill("Sensor-1")).thenReturn(waterBill);
        Response result = meterController.getWaterBill(meter.getMeterName());
        Assertions.assertThat(result.getCode()).isEqualTo(200);

    }

    @Test
    public void setVolume() {
        User user = new User();
        user.setUsername("member");
        user.setPassword("1234");
        user.setId(12);

        Member member = new Member();
        member.setId(1);

        Meter meter = new Meter();
        meter.setId(1);


        Volume volume = new Volume();
        volume.setMember_id(1);
        volume.setMeter_id(1);


        long newVolumeNum = 1200;


        Mockito.when(userMapper.selectUserById(12)).thenReturn(user);
        Mockito.when(roleManager.isMember(12)).thenReturn(true);
        Mockito.when(memberMapper.selectMemberById(volume.getMember_id())).thenReturn(member);
        Mockito.when(meterMapper.selectMeterById(volume.getMeter_id())).thenReturn(meter);
        Mockito.when(volumeMapper.selectVolumeById(volume.getMeter_id(),volume.getMeter_id())).thenReturn(volume);
        Mockito.when(meterService.setMemberVolume(volumeMapper.selectVolumeById(member.getId(),meter.getId()),newVolumeNum)).thenReturn(true);
        Response result = meterController.setVolume("MEMBERQQQWWW",member.getId(),meter.getId(),newVolumeNum);
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }


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


        Mockito.when(meterService.getMeterAndMember()).thenReturn(Collections.singletonList(meter));
        Response result = meterController.getMeterAndMember();
        Assertions.assertThat(result.getCode()).isEqualTo(200);


    }

    @Test
    public void updateMeter() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setId(11);

        MeterRequest meterRequest = new MeterRequest();
        meterRequest.setMeter_id(1);
        meterRequest.setMeterName("senor-1");
        meterRequest.setMeterDesc("g34");
        meterRequest.setMember_id(1);
        meterRequest.setRoom("b123");
        meterRequest.setContact("342423@qq.com");
        meterRequest.setName("tete");

        Mockito.when(userMapper.selectUserById(11)).thenReturn(user);
        Mockito.when(roleManager.isAdmin(11)).thenReturn(true);
        Mockito.when(meterService.updateMeter(meterRequest)).thenReturn(true);
        Response result = meterController.updateMeter("QQQWWWEEE",meterRequest);
        Assertions.assertThat(result.getCode()).isEqualTo(200);


    }

    @Test
    public void deleteMeter() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setId(11);
        Meter meter = new Meter();
        meter.setId(1);
        Member member = new Member();
        member.setId(1);

        Mockito.when(userMapper.selectUserById(11)).thenReturn(user);
        Mockito.when(roleManager.isAdmin(11)).thenReturn(true);
        Mockito.when(meterService.deleteMeter(1,1)).thenReturn(true);
        Response result = meterController.deleteMeter("QQQWWWEEE",1,1);
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }

    @Test
    public void addMeter() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setId(11);
        MeterRequest meterRequest = new MeterRequest();
        meterRequest.setName("tete");
        meterRequest.setContact("123131@qq.com");
        meterRequest.setRoom("b123");
        meterRequest.setMeterName("sensor-1");
        meterRequest.setMeterDesc("g3&4");

        Mockito.when(userMapper.selectUserById(11)).thenReturn(user);
        Mockito.when(roleManager.isAdmin(11)).thenReturn(true);
        Mockito.when(meterService.addMeter(meterRequest)).thenReturn(meterRequest);

        Response result = meterController.addMeter("QQQWWWEEE",11,meterRequest);
        Assertions.assertThat(result.getCode()).isEqualTo(200);
    }
}