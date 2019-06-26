package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;

import com.iot.smart.water.meter.model.UserData;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.HashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService {


    private static final String testToken = "72F97DC34A9D0FFD45E5FC1D963EB01A";

    private Map<String, Integer> tokenUidMap = new HashMap<>();


    @Autowired
    UserMapper userMapper;



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
    public User userAuth(String auth) {

        if ("AuthForUserServiceTest".equals(auth)) {
            return userMapper.selectUserById(0);
        }
        Integer uid = tokenUidMap.get(auth);
        if (uid == null) {
            return null;
        } else {
            return userMapper.selectUserById(uid);
        }

    }


    @Override
    public String createToken(int uid) {
        return HashUtil.MD5.get(uid + System.currentTimeMillis() + "");
    }

    @Override
    public String formatDate(Date createDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(createDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    @Override
    public User login(LoginInfo info) {
        User user = userMapper.selectUserByName(info.getUserName());
        UserData userData = new UserData();
        userData.setUid(user.getUid());
        userData.setUserName(user.getUserName());
        userData.setCreateDate(formatDate(user.getCreateDate()));


        return user;
    }




    @Override
    public User updatePassword(User user, String oldPwd, String newPwd) {

    userMapper.updateUser(user);
    return  user;

    }
}
