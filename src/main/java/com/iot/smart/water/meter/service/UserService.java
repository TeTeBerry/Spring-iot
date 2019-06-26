package com.iot.smart.water.meter.service;


import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.Response;

import java.util.Date;
import java.util.List;


public interface UserService {



      User updatePassword(User user, String oldPwd, String newPwd);

      User userAuth(String auth);

      User login(LoginInfo info);

      User register(User user);

      String createToken(int uid);

      String formatDate(Date createDate);

}