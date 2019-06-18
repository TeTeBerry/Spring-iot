package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.MeterService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/iot/meter")
public class MeterController {

    @Autowired
    private MeterService mService;

    @GetMapping(value = "/getMeters")
    @CrossOrigin(origins = "*")
    public Response getMeters() {
        return mService.getMeters();
    }

//    @PostMapping(value = "/add")
//    @CrossOrigin(origins = "*")
//    public Response addMeter(@RequestBody Meter meter) {
//        return mService.getMeters();
//    }

    @PostMapping(value = "/update")
    @CrossOrigin(origins = "*")
    public Response update(@RequestBody Meter meter) {
        // TODO need auth in header to verify token?
        return mService.updateMeter(meter);
    }

    @DeleteMapping(value = "/delete")
    @CrossOrigin(origins = "*")
    public Response delete(@RequestParam("mid") int mid) {
        // TODO need auth in header to verify token?
        return mService.deleteMeter(mid);
    }
}
