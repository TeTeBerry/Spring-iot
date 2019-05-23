package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/iot/user")
public class UserController {

    @Autowired
    private UserService mService;

    @PostMapping(value = "/register")
    @CrossOrigin(origins = "*")
    public Response register(@RequestBody User user) {
        return mService.register(user);
    }

    @PostMapping(value = "/login")
    @CrossOrigin(origins = "*")
    public Response login(@RequestParam("userName") String userName,
                          @RequestParam("password") String password) {
        return mService.login(userName, password);
    }

    @PostMapping(value = "/update")
    @CrossOrigin(origins = "*")
    public Response update(@RequestBody() User user) {
        return mService.update(user);
    }

    @PostMapping(value = "addMeter/{uid}")
    @CrossOrigin(origins = "*")
    public Response addMeter(@PathVariable("uid") String uid,
                             @RequestParam("token") String token,
                             @RequestParam("meterId") String meterId){
        return mService.addMeter(uid, token, meterId);
    }

    @DeleteMapping(value = "/delete/{uid}")
    @CrossOrigin(origins = "*")
    public Response delete(@PathVariable("uid") String uid,
                           @RequestParam("token") String token,
                           @RequestParam("pwd") String pwd) {
        return mService.delete(uid, token, pwd);
    }
}
