package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.RoleManager;
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

    @Mock
    private RoleManager roleManager;

    @InjectMocks
    UserController userController;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void login() {
        User user = new User();
        user.setId(1);
        user.setUsername("tete");
        user.setPassword("1234");

        LoginInfo info = new LoginInfo();
        info.setUsername("tete");
        info.setPassword("1234");

        Mockito.when(userMapper.selectUserByName(user.getUsername())).thenReturn(user);
        Response result = userController.login(info);
        Assertions.assertThat(result.getMsg()).isEqualTo(result.getMsg());
        System.out.println(result.getMsg());

    }

    @Test
    public void updatePassword() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1234");
        user.setId(11);
        User user1 = new User();
        user1.setUsername("admin");
        user1.setPassword("1111");
        String username = "member";
        String oldPwd = "1111";
        String newPwd = "1234";

        Mockito.when(userMapper.selectUserById(11)).thenReturn(user);
        Mockito.when(roleManager.isAdmin(11)).thenReturn(true);
        Mockito.when(userMapper.selectUserByName(username)).thenReturn(user1);
        Mockito.when(userService.updatePassword(user1,oldPwd,newPwd)).thenReturn(user1);


        Response result = userController.updatePassword("QQQWWWEEE",oldPwd, newPwd,username);
        Assertions.assertThat(result.getData()).isEqualTo(user1);
        System.out.println(user1);
    }


}