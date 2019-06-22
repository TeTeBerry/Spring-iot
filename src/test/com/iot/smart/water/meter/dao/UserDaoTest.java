package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.User;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @Test
    @Transactional
    public void insertMeter() {
        User user = new User();
        user.setUserName("tt");
        user.setPassword("123");
        user.setCreateDate(new Date());

        int result = userDao.insertUser(user);
        Assertions.assertThat(result).isEqualTo(1);
    }

}