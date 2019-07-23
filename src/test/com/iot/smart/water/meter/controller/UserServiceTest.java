package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.RoleMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.dao.UserRoleMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;

import com.iot.smart.water.meter.service.Impl.UserServiceImpl;
import com.iot.smart.water.meter.service.UserService;

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

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Configuration
    static class UserServiceConfig {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private UserRoleMapper userRoleMapper;

    @MockBean
    private RoleMapper roleMapper;

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updatePassword() {
        User user = new User();
        user.setUsername("tete");
        user.setPassword("1234");
        String oldPwd = "1234";
        String newPwd = "1234";

        Mockito.when(userMapper.updateUser(user)).thenReturn(1);
        User result = userService.updatePassword(user, oldPwd, newPwd);
        Assertions.assertThat(result).isEqualTo(user);
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
        User result = userService.login(info);
        Assertions.assertThat(result).isEqualTo(user);
    }

    @Test
    public void register() {
        User user = new User();
        user.setUsername("test");
        user.setPassword("1234");
        user.setCreated_at(new Date());

        Mockito.when(userMapper.insertUser(user)).thenReturn(1);
        User result = userService.register(user);
        Assertions.assertThat(result).isEqualTo(user);
    }
}
