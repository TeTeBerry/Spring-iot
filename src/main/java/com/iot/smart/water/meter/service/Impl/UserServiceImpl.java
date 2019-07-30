package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.RoleMapper;
import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.dao.UserRoleMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;

import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.RoleManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

//    @Override
//    @Transactional(rollbackFor = {Exception.class})
//    public User register(User user) {
//        if (user.getId() != null) {
//            user.setId(null);
//        }
//        user.setCreated_at(new Date());
//        userMapper.insertUser(user);
//        userRoleMapper.insertUidAndRid(user.getId(), roleMapper.selectRoleIdByName(RoleManager.ROLE_ADMIN));
//        return user;
//    }

    @Override
    public User login(LoginInfo info) {
        return userMapper.selectUserByName(info.getUsername());
    }

    @Override
    public User updatePassword(User user, String oldPwd, String newPwd) {
        userMapper.updateUser(user);
        return user;
    }
}
