package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;

import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private static final String USER_ADMIN = "admin";

    @Override
    public User register(User user) {
        if (user.getUid() != null) {
            user.setUid(null);
        }
        user.setCreateDate(new Date());
        try {
            userMapper.insertUser(user);
        } catch (Exception e) {
            userMapper.createTable();
            userMapper.insertUser(user);
        }
        return user;
    }

    @Override
    public String createToken(int uid) {
        return HashUtil.MD5.get(uid + System.currentTimeMillis() + "");
    }

    @Override
    public User login(LoginInfo info) {
        return userMapper.selectUserByName(info.getUserName());
    }

    @Override
    public User updatePassword(User user, String oldPwd, String newPwd) {
        userMapper.updateUser(user);
        return user;
    }
}
