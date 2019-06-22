package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Meter;
import org.assertj.core.api.Assertions;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 *
 * Created by Chenziyu on 2019/6/22
 **/
@RunWith(SpringRunner.class)
@MybatisTest
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)

public class MeterDaoTest {


    @Autowired
    private MeterDao meterDao;


    @Test
    public void /*insertMeter*/ test1() throws Exception {
        Meter meter = new Meter();
        meter.setMid(2);
        meter.setMeterDesc("HangZhou");
        meter.setMemberContact("23432432@qq.com");
        meter.setMeterName("sensor1");
        meter.setRoom("B234");
        meter.setMemberName("cee");

        int result = meterDao.insertMeter(meter);
        Assertions.assertThat(result).isEqualTo(1);
    }


}