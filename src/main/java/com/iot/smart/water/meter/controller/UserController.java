package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.UserMapper;
import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.UserService;
import com.iot.smart.water.meter.util.RoleManager;
import com.iot.smart.water.meter.util.TokenUtil;
import com.iot.smart.water.meter.util.line.LineNotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iot/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private DataService dataService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private LineNotify lineNotify;

    @Autowired
    private RoleManager roleManager;

	@PostMapping("/register")
	@CrossOrigin(origins="*")
    public Response register(@RequestBody User user) {
        Response response = new Response();
        if (StringUtils.isEmpty(user.getUsername())) {
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
        if (StringUtils.isEmpty(info.getUsername())) {
            response.setCode(ErrorCode.EMPTY_USERNAME);
            response.setMsg("empty username");
            return response;
        }
        User user = userMapper.selectUserByName(info.getUsername());
        if (user == null) {
            response.setCode(ErrorCode.INVALID_USERNAME);
            response.setMsg("invalid username");
            return response;
        } else {
            if (!user.getPassword().equals(info.getPassword())) {
                response.setCode(ErrorCode.INVALID_PASSWORD);
                response.setMsg("invalid password");
                return response;
            } else {
                userService.login(info);
                if (info.getUsername().contains("member")) {
                    lineNotify.notifyMe("You have been login IoT Water System web application", 2, 1);
                }

                response.setMsg(TokenUtil.createToken(user.getId()));
                response.setData(userService.login(info));
                return response;
            }
        }
    }

	@PostMapping("/updatePassword")
	@CrossOrigin(origins="*")
    public Response updatePassword(@RequestHeader("token") String token,
                                   @RequestParam("oldPwd") String oldPwd,
                                   @RequestParam("newPwd") String newPwd) {
        Response response = new Response();
        Integer id = TokenUtil.getId(token);
        if (id == null) {
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        User user = userMapper.selectUserById(id);
        if (user == null) {
            TokenUtil.clear(id, token);
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        if (!roleManager.isAdmin(user.getId())) {
            response.setCode(ErrorCode.PERMISSION_ERROR);
            response.setMsg("permission error");
            return response;
        }

        if (StringUtils.isEmpty(user.getUsername())) {
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