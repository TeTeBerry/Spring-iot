package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Meter;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MeterDaoTest {

    @Autowired
    private MeterDao meterDao;

    @Test
    @Transactional
    public void insertMeter() {
        Meter meter = new Meter();
        meter.setMeterDesc("test");
        meter.setMemberContact("123@qq.com");
        meter.setMeterName("sensor0");
        meter.setRoom("B234");
        meter.setMemberName("test");
        meter.setCreateDate(new Date());

        int result = meterDao.insertMeter(meter);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    @Transactional
    public void deleteMeter() {
        int result = meterDao.deleteMeter(1);
        Assertions.assertThat(result).isEqualTo(1);
    }

    @Test
    public void selectAllMeter() {
        List<Meter> meters = meterDao.selectAllMeter();
        // count in meter table
        Assertions.assertThat(meters.size()).isEqualTo(2);
    }

}