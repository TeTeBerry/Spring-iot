package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.dao.UserDao;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.ResponseUser;
import com.iot.smart.water.meter.util.HashUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private MeterDao meterDao;

    private Map<String, String> userTokenMap = new HashMap<>();

    public boolean isTokenVaild(String uid, String token) {
        String userToken = userTokenMap.get(uid);
        return !StringUtils.isEmpty(userToken) && userToken.equals(token);
    }

    public Response addMeter(String uid, String token, String meterId) {
        Response<ResponseUser> response = new Response<>();
        if (!isTokenVaild(uid, token)) {
            response.setResultCode(ErrorCode.INVALID_TOKEN);
            response.setResultMsg("invalid token");
            return response;
        }

        Meter meter = meterDao.selectMeter(meterId);
        if (meter == null) {
            response.setResultCode(ErrorCode.INVALID_MID);
            response.setResultMsg("invalid mid");
            return response;
        }

        User user = userDao.selectUserById(uid);
        if (user == null) {
            response.setResultCode(ErrorCode.INVALID_UID);
            response.setResultMsg("invalid uid");
            return response;
        }

        user.setMid(meterId);
        userDao.updateUser(user);
        response.setResultMsg("add meter success");
        response.setData(convertUser(user));
        return response;
    }

    public Response update(User user) {
        Response<User> response = new Response<>();
        if (!isTokenVaild(user.getUid(), user.getToken())) {
            response.setResultCode(ErrorCode.INVALID_TOKEN);
            response.setResultMsg("invalid token");
            return response;
        }

        User userInDB = userDao.selectUserById(user.getUid());
        if (userInDB == null) {
            response.setResultCode(ErrorCode.INVALID_UID);
            response.setResultMsg("invalid uid");
            return response;
        }

        userDao.updateUser(user);
        response.setResultMsg("update user success");
        user.setPassword("");
        response.setData(user);
        return response;
    }

    public Response login(String userName, String password) {
        Response<ResponseUser> response = new Response<>();
        User user = userDao.selectUser(userName, password);
        if (user != null) {
            String updateToken = createToken(user.getUid());
            user.setToken(updateToken);
            userDao.updateUser(user);
            response.setResultMsg("login success");
            user.setPassword("");
            response.setData(convertUser(user));
        } else {
            String pwd = userDao.selectUserNameForPwd(userName);
            if (StringUtils.isEmpty(pwd)) {
                response.setResultCode(ErrorCode.INVALID_USERNAME);
                response.setResultMsg("invalid userName");
            } else {
                response.setResultCode(ErrorCode.INVALID_PASSWORD);
                response.setResultMsg("invalid password");
            }
        }
        return response;
    }

    public Response register(User user) {
        Response<String> response = new Response<>();
        user.setUid(createUid());
        user.setCreateDate(new Date());
        user.setToken(createToken(user.getUid()));
        try {
            userDao.insertUser(user);

            userTokenMap.put(user.getUid(), user.getToken());
            response.setResultMsg(user.getToken());
        } catch (Exception e) {

            userDao.createTable();
            userDao.insertUser(user);
            userTokenMap.put(user.getUid(), user.getToken());
            response.setResultMsg("register success");
            response.setData(user.getToken());
        }
        return response;
    }

    public Response delete(String uid, String token, String pwd) {
        Response response = new Response();

        if (!isTokenVaild(uid, token)) {
            response.setResultCode(ErrorCode.INVALID_TOKEN);
            response.setResultMsg("invalid token");
            return response;
        }

        User userInDB = userDao.selectUserById(uid);
        if (userInDB == null) {
            response.setResultCode(ErrorCode.INVALID_UID);
            response.setResultMsg("invalid uid");
            return response;
        }

        if (!userInDB.getPassword().equals(pwd)) {
            response.setResultCode(ErrorCode.INVALID_PASSWORD);
            response.setResultMsg("invalid password");
            return response;
        }

        userDao.deleteUser(uid);
        response.setResultMsg("delete user success");
        return response;
    }

    public String createToken(String uid) {
        return HashUtil.MD5.get(uid + System.currentTimeMillis());
    }

    public String createUid() {
        return UUID.randomUUID().toString();
    }

    private ResponseUser convertUser(User user) {
        ResponseUser responseUser = new ResponseUser();
        responseUser.setUid(user.getUid());
        responseUser.setUserName(user.getUserName());
        responseUser.setFirstName(user.getFirstName());
        responseUser.setLastName(user.getLastName());
        responseUser.setEmail(user.getEmail());
        responseUser.setMid(user.getMid());
        responseUser.setToken(user.getToken());
        responseUser.setCreateDate(formatDate(user.getCreateDate()));
        return responseUser;
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
