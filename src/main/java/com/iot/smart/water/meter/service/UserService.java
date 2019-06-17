package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.dao.UserDao;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.UserData;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    private static final String USER_ADMIN = "admin";

    @Autowired
    private UserDao userDao;

    @Autowired
    private MeterDao meterDao;

    @Autowired
    private MeterService meterService;

    private Map<String, Integer> tokenUidMap = new HashMap<>();
    private Map<Integer, String> uidTokenMap = new HashMap<>();


    public Response addMeter(Meter meter) {
        return meterService.addMeter(meter);
    }

    public Response updatePassword(User user, String oldPwd, String newPwd) {
        Response response = new Response();
        if (!user.getPassword().equals(oldPwd)) {
            response.setCode(ErrorCode.INVALID_PASSWORD);
            response.setMsg("old password not match");
            return response;
        }
        user.setPassword(newPwd);
        userDao.updateUser(user);

        response.setMsg("update password success");
        return response;
    }

    public User userAuth(String auth) {
        Integer uid = tokenUidMap.get(auth);
        if (uid == null) {
            return null;
        } else {
            return userDao.selectUserById(uid);
        }
    }

    public Response login(LoginInfo info) {
        Response<UserData> response = new Response<>();
        if (StringUtils.isEmpty(info.getUserName())) {
            response.setCode(ErrorCode.EMPTY_USERNAME);
            response.setMsg("empty userName");
            return response;
        }
        User user = userDao.selectUserByName(info.getUserName());
        if (user == null) {
            response.setCode(ErrorCode.INVALID_USERNAME);
            response.setMsg("invalid userName");
            return response;
        } else {
            if (!user.getPassword().equals(info.getPassword())) {
                response.setCode(ErrorCode.INVALID_PASSWORD);
                response.setMsg("invalid password");
                return response;
            } else {
                UserData userData = new UserData();
                userData.setUid(user.getUid());
                userData.setUserName(user.getUserName());
                userData.setCreateDate(formatDate(user.getCreateDate()));
                response.setData(userData);

                String token = uidTokenMap.get(user.getUid());
                if (token != null) {
                    tokenUidMap.remove(token);
                }
                token = createToken(user.getUid());
                tokenUidMap.put(token, user.getUid());
                uidTokenMap.put(user.getUid(), token);

                response.setMsg(token);
                return response;
            }
        }
    }

    public Response register(User user) {
        Response response = new Response();
        if (user.getUid() != null) {
            user.setUid(null);
        }
        user.setCreateDate(new Date());
        if (StringUtils.isEmpty(user.getUserName())) {
            response.setCode(ErrorCode.EMPTY_USERNAME);
            response.setMsg("empty userName");
            return response;
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            response.setCode(ErrorCode.EMPTY_PASSWORD);
            response.setMsg("empty password");
            return response;
        }
        try {
            userDao.insertUser(user);
        } catch (Exception e) {
            userDao.createTable();
            userDao.insertUser(user);
        }
        response.setMsg("register success");
        return response;
    }

    public String createToken(int uid) {
        return HashUtil.MD5.get(uid + System.currentTimeMillis() + "");
    }

    private String formatDate(Date createDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(createDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}