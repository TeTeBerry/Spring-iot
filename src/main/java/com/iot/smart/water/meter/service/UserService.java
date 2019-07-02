package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.User;


public interface UserService {

    User updatePassword(User user, String oldPwd, String newPwd);

    User login(LoginInfo info);

    User register(User user);

    String createToken(int uid);
}