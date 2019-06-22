package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.dao.UserDao;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.Response;

import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @MockBean
    private UserDao userDao;

    @MockBean
    private MeterDao meterDao;

    @Mock
    private MeterService meterService;

    @InjectMocks
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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

        Mockito.when(meterService.addMeter(meter)).thenReturn(new Response());
        Response response = userService.addMeter(meter);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

    @Test
    public void updatePassword() {
        User user = new User();
        user.setUserName("test");
        user.setPassword("123");
        String oldPwd = "123";
        String newPwd = "123456";

        Mockito.when(userDao.updateUser(user)).thenReturn(1);
        Response response = userService.updatePassword(user, oldPwd, newPwd);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

    @Test
    public void userAuth() {
        String auth = "AuthForUserServiceTest";
        User user = new User();
        user.setUserName("test");

        Mockito.when(userDao.selectUserById(0)).thenReturn(user);
        User resultUser = userService.userAuth(auth);
        Assertions.assertThat(resultUser.getUserName()).isEqualTo(user.getUserName());
    }

    @Test
    public void login() {
        LoginInfo info = new LoginInfo();
        info.setUserName("tete");
        info.setPassword("123");
        User user = new User();
        user.setUid(1);
        user.setUserName("tete");
        user.setPassword("123");
        user.setCreateDate(new Date());

        Mockito.when(userDao.selectUserByName("tete")).thenReturn(user);
        Response response = userService.login(info);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

    @Test
    public void register() {
        User user = new User();
        user.setUserName("tete");
        user.setPassword("123");
        user.setCreateDate(new Date());

        Mockito.when(userDao.insertUser(user)).thenReturn(1);
        Response response = userService.register(user);
        Assertions.assertThat(response.getCode()).isEqualTo(200);
    }

}