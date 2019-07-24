package com.iot.smart.water.meter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;


public class UserControllerUnitTest {


    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void register() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setId(1);

        Mockito.when(userService.register(user)).thenReturn(user);
        Response result = userController.register(user);
        Assertions.assertThat(result.getCode()).isEqualTo(200);

    }

    @Test
    public void login() {
        LoginInfo info = new LoginInfo();
        info.setUsername("tete");
        info.setPassword("123");
        User user = new User();
        user.setId(1);
        user.setUsername("tete");
        user.setPassword("123");
        user.setCreated_at(new Date());

        Mockito.when(userMapper.selectUserByName("tete")).thenReturn(user);
        Response result = userController.login(info);
        Assertions.assertThat(result.getCode()).isEqualTo(200);


    }

//    @Test
//    public void updatePassword() {
//        User user = new User();
//        user.setUsername("tete");
//        user.setPassword("1234");
//        user.setId(1);
//        String username = "test";
//        String oldPwd = "1234";
//        String newPwd = "1234";
//        String token = "2f0e75d660199693153312156583df13";
//
//        Mockito.when(userMapper.updateUser(user)).thenReturn(1);
//        Response result = userController.updatePassword(token,username,oldPwd, newPwd);
//        Assertions.assertThat(result.getCode()).isEqualTo(200);
//    }


}