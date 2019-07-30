package com.iot.smart.water.meter.controller;


import com.iot.smart.water.meter.dao.MemberMapper;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;
import com.iot.smart.water.meter.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/iot/data")
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * @param meterName
     * @param date
     */
    @GetMapping("/getDailyData")
    @CrossOrigin(origins = "*")
    public Response getDailyData(@RequestParam("meterName") String meterName,
                                 @RequestParam("date") String date) {
        Response response = new Response();
        List<DailyData> dailyDataList = dataService.getDailyData(meterName, date);
        if (dailyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(dailyDataList);
        response.setMsg("get daily data success");
        return response;
    }


    /**
     * @param meterName
     * @param date
     */
    @GetMapping("/getMonthlyData")
    @CrossOrigin(origins = "*")
    public Response getMonthlyData(@RequestParam("meterName") String meterName,
                                   @RequestParam("date") String date) {
        Response response = new Response();
        List<MonthlyData> monthlyDataList = dataService.getMonthlyData(meterName, date);
        if (monthlyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(monthlyDataList);
        response.setMsg("get monthly data success");
        return response;
    }

    /**
     * @param meterName
     * @param date
     */
    @GetMapping("/getWeeklyData")
    @CrossOrigin(origins = "*")
    public Response getWeeklyData(@RequestParam("meterName") String meterName,
                                  @RequestParam("date") String date) {
        Response response = new Response();
        List<WeeklyData> weeklyDataList = dataService.getWeeklyData(meterName, date);
        if (weeklyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(weeklyDataList);
        response.setMsg("get weekly data success");
        return response;
    }


}
