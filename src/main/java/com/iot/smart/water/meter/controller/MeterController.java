package com.iot.smart.water.meter.controller;


import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;

import com.iot.smart.water.meter.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/iot/meter")
public class MeterController {

    @Autowired
    private MeterService mService;


    @Autowired
    private MeterMapper meterMapper;

    @GetMapping(value = "/getMeters")
    @CrossOrigin(origins = "*")
    public Response getMeters() {
        Response<List<Meter>> response = new Response<>();
        response.setMsg("get meter success");
        response.setData(mService.getMeters());
        return response;
    }



    @PostMapping(value = "/update")
    @CrossOrigin(origins = "*")
    public Response updateMeter(@RequestBody Meter meter) {
        // TODO need auth in header to verify token?
        Response response = new Response();

        if (mService.updateMeter(meter) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else if (StringUtils.isEmpty(meter.getMeterName())) {
            response.setCode(ErrorCode.EMPTY_MeterName);
            response.setMsg("empty meterName");
            return response;
        } else  if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_MeterDesc);
            response.setMsg("empty meterDesc");
            return response;
        }else
        if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_MemberName);
            response.setMsg("empty memberName");
            return response;

        }else if (StringUtils.isEmpty(meter.getRoom())) {
            response.setCode(ErrorCode.EMPTY_Room);
            response.setMsg("empty room");
            return response;

        }else if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))){

            response.setCode(ErrorCode.EMPTY_MeterContact);
            response.setMsg("email invalid");
            return response;
        }


            meterMapper.updateMeter(meter);

        response.setMsg("update meter success");
        response.setData(mService.updateMeter(meter));
        return response;

    }

    @DeleteMapping(value = "/delete")
    @CrossOrigin(origins = "*")
    public Response deleteMeter(@RequestParam("mid") int mid) {
        // TODO need auth in header to verify token?
        Response response = new Response();

        if (mService.deleteMeter(mid) == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterMapper.deleteMeterById(mService.deleteMeter(mid).getMid());
        }
        response.setMsg("delete success");
        return response;
    }
}
