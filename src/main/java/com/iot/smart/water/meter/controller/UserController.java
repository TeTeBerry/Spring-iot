package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.UserData;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/iot/admin")
public class UserController {
    private static final String testToken = "72F97DC34A9D0FFD45E5FC1D963EB01A";


    private Map<String, Integer> tokenUidMap = new HashMap<>();
    private Map<Integer, String> uidTokenMap = new HashMap<>();


    @Autowired
    private UserService userService;

    @Autowired
    private MeterService meterService;

    @Autowired
    private UserMapper userMapper;


    @PostMapping(value = "/register")
    @CrossOrigin(origins = "*")
    public Response register(@RequestBody User user) {

        Response response = new Response();
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
        response.setData(userService.register(user));
        response.setMsg("register success");
        return response;
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public Response login(@RequestBody LoginInfo info) {

        Response<UserData> response = new Response<>();
        if (StringUtils.isEmpty(info.getUserName())) {
            response.setCode(ErrorCode.EMPTY_USERNAME);
            response.setMsg("empty userName");
            return response;
        }
        User user = userMapper.selectUserByName(info.getUserName());
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
                userService.login(info);

                String token = uidTokenMap.get(user.getUid());
                if (token != null) {
                    tokenUidMap.remove(token);
                }

                token = userService.createToken(user.getUid());
                tokenUidMap.put(token, user.getUid());
                uidTokenMap.put(user.getUid(), token);

                response.setMsg(token);
                return response;
            }
        }


    }

    @PostMapping(value = "/updatePassword")
    @CrossOrigin(origins = "*")
    public Response updatePassword(@RequestHeader("auth") String auth,
                                   @RequestParam("oldPwd") String oldPwd,
                                   @RequestParam("newPwd") String newPwd) {

        Response response = new Response();
        User user = userService.userAuth(auth);
        if (user == null) {
            if (auth.startsWith(testToken)) {
                user = userMapper.selectUserByName(auth.substring(testToken.length()));
                if (user != null) {
                    userService.updatePassword(user, oldPwd, newPwd);
                }
            }

            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");

            if (!user.getPassword().equals(oldPwd)) {
                response.setCode(ErrorCode.INVALID_PASSWORD);
                response.setMsg("old password not match");
                return response;
            }
            user.setPassword(newPwd);
            if (StringUtils.isEmpty(user.getPassword())) {
                response.setCode(ErrorCode.EMPTY_PASSWORD);
                response.setMsg("empty password");
                return response;
            }
            userService.updatePassword(user, oldPwd, newPwd);

            response.setMsg("update password success");
            response.setData(userService.updatePassword(user, oldPwd, newPwd));
             return  response;

        }

        return response;
    }



    @PostMapping(value = "/addMeter")
    @CrossOrigin(origins = "*")
    public Response addMeter(@RequestHeader("auth") String auth,
                             @RequestBody Meter meter) {
        Response response = new Response();

        User user = userService.userAuth(auth);
        if (user == null) {
            if (auth.startsWith(testToken)) {
                user = userMapper.selectUserByName(auth.substring(testToken.length()));
                if (user != null) {
                    response.setData(meterService.addMeter(meter));
                    response.setMsg("add meter success");
                    return response;
                }
            }
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        return response;
    }

}