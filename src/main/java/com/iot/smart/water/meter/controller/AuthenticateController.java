package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Member;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/iot/auth")
public class AuthenticateController {

    @Autowired
    private DataService dataService;

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private MemberMapper memberMapper;

    @PostMapping("/getReportAuthorize")
    @CrossOrigin(origins = "*")
    public Response getReportAuthorize(@RequestParam("meterName") String meterName,
                              @RequestParam("password") String password) {
        Response response = new Response();
        Meter meter = meterMapper.selectMeterByName(meterName);
        Member member = memberMapper.selectMemberById(meter.getMember_id());
        if (!member.getPassword().equals(password)){
            response.setCode(ErrorCode.INVALID_PASSWORD);
            response.setMsg("invalid password");
            return response;
        }
        response.setData(dataService.getAuthorize(member,meterName));
        response.setMsg("get report authorize");
        return response;
    }

    @PostMapping("/getWaterBillAuthorize")
    @CrossOrigin(origins = "*")
    public Response getWaterBillAuthorize(@RequestParam("meterName") String meterName,
                              @RequestParam("password") String password) {
        Response response = new Response();
        Meter meter = meterMapper.selectMeterByName(meterName);
        Member member = memberMapper.selectMemberById(meter.getMember_id());
        if (!member.getPassword().equals(password)){
            response.setCode(ErrorCode.INVALID_PASSWORD);
            response.setMsg("invalid password");
            return response;
        }
        response.setData(dataService.getAuthorize(member,meterName));
        response.setMsg("get water bill authorize");
        return response;
    }

}
