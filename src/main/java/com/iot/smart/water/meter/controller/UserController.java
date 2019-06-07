package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.LoginInfo;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/iot/admin")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register")
    @CrossOrigin(origins = "*")
    public Response register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public Response login(@RequestBody LoginInfo info) {
        return userService.login(info);
    }

    @PostMapping(value = "/updatePassword")
    @CrossOrigin(origins = "*")
    public Response updatePassword(@RequestHeader("auth") String auth,
                                   @RequestParam("oldPwd") String oldPwd,
                                   @RequestParam("newPwd") String newPwd) {
        User user = userService.userAuth(auth);
        if (user == null) {
            Response response = new Response();
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        return userService.updatePassword(user, oldPwd, newPwd);
    }

    @PostMapping(value = "/addMeter")
    @CrossOrigin(origins = "*")
    public Response addMeter(@RequestHeader("auth") String auth,
                             @RequestBody Meter meter){
        User user = userService.userAuth(auth);
        if (user == null) {
            Response response = new Response();
            response.setCode(ErrorCode.INVALID_TOKEN);
            response.setMsg("invalid token");
            return response;
        }
        return userService.addMeter(meter);
    }

}
