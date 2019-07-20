package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.line.LineNotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/iot/admin")
public class UserController {

    private Map<String, Integer> tokenUidMap = new HashMap<>();
    private Map<Integer, String> uidTokenMap = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private DataService dataService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LineNotify lineNotify;

	@PostMapping("/register")
	@CrossOrigin(origins="*")
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

	@PostMapping("/login")
	@CrossOrigin(origins="*")
    public Response login(@RequestBody LoginInfo info) {

        Response<User> response = new Response<>();
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
                if (info.getUserName().contains("member")) {
                    lineNotify.notifyMe("You have been login IoT Water System web application", 2, 1);
                }

                String token = uidTokenMap.get(user.getUid());
                if (token != null) {
                    tokenUidMap.remove(token);
                }

                token = userService.createToken(user.getUid());
                tokenUidMap.put(token, user.getUid());
                uidTokenMap.put(user.getUid(), token);

                response.setMsg("token"+token);
                response.setData(userService.login(info));
                return response;
            }
        }
    }

	@PostMapping("/updatePassword")
	@CrossOrigin(origins="*")
    public Response updatePassword(@RequestParam("userName") String userName,
                                   @RequestParam("oldPwd") String oldPwd,
                                   @RequestParam("newPwd") String newPwd) {
        Response response = new Response();
        User user = userMapper.selectUserByName(userName);
        if (user == null) {
            response.setCode(ErrorCode.INVALID_USERNAME);
            response.setMsg("invalid userName");
            return response;
        }
        if (StringUtils.isEmpty(user.getUserName())) {
            response.setCode(ErrorCode.EMPTY_USERNAME);
            response.setMsg("empty userName");
            return response;
        }
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
        user.setPassword(newPwd);
        if (user.getPassword().length()<4){
            response.setCode(ErrorCode.NEWPASSWORDDIGIT);
            response.setMsg("at least 4 digits");
            return response;
        }

        userService.updatePassword(user, oldPwd, newPwd);
        response.setMsg("update password success");
        response.setData(userService.updatePassword(user, oldPwd, newPwd));
        return response;
    }
}