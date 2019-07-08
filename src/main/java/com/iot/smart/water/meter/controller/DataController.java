package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/iot/data")
public class DataController {
    @Autowired
    private DataService dataService;

    @GetMapping(value = "/getSensorData")
    @CrossOrigin(origins = "*")
    public Response getSensorData() {
        Response<List<Data>> response = new Response<>();
        response.setMsg("get sensorData success");
        response.setData(dataService.getSensorData());
        return response;
    }
}
