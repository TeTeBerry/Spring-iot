package com.iot.smart.water.meter.controller;

import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.DailyData;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.MonthlyData;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.model.WeeklyData;
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

    @GetMapping(value = "/getMonthlyData")
    @CrossOrigin(origins = "*")
    public Response getMonthlyData(@RequestParam("meterName") String meterName,
                                   @RequestParam("date") String date) {
        Response response = new Response();
        List<MonthlyData> monthlyDataList = mService.getMonthlyDatas(meterName, date);
        if (monthlyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(monthlyDataList);
        return response;
    }

    @GetMapping(value = "/getWeeklyData")
    @CrossOrigin(origins = "*")
    public Response getWeeklyData(@RequestParam("meterName") String meterName,
                                 @RequestParam("date") String date) {
        Response response = new Response();
        List<WeeklyData> weeklyDataList = mService.getWeeklyDatas(meterName, date);
        if (weeklyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(weeklyDataList);
        return response;
    }

    @GetMapping(value = "/getDailyData")
    @CrossOrigin(origins = "*")
    public Response getDailyData(@RequestParam("meterName") String meterName,
                                 @RequestParam("date") String date) {
        Response response = new Response();
        List<DailyData> dailyDataList = mService.getDailyDatas(meterName, date);
        if (dailyDataList == null) {
            response.setCode(ErrorCode.INVALID_FORMAT_DATE);
            response.setMsg("invalid format date");
            return response;
        }
        response.setData(dailyDataList);
        return response;
    }

    @GetMapping(value = "/getWaterBill")
    @CrossOrigin(origins = "*")
    public Response getWaterBill() {
        Response response = new Response();
        List<WaterBill> list = mService.getWaterBill();
        response.setData(list);
        response.setMsg("get water bill success");
        return response;
    }

    @PostMapping(value = "/setMemberVolume")
    @CrossOrigin(origins = "*")
    public Response setMemberVolume(@RequestParam("memberName") String memberName,
                                    @RequestParam("volume") float volume) {
        Response response = new Response();
        if (!mService.setMemberVolume(memberName, volume)) {
            response.setCode(ErrorCode.INVALID_PARAMS);
            response.setMsg("invalid params");
            return response;
        }
        response.setMsg("update member volume success");
        return response;
    }

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
            response.setCode(ErrorCode.EMPTY_METERNAME);
            response.setMsg("empty meterName");
            return response;
        } else if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_METERDESC);
            response.setMsg("empty meterDesc");
            return response;
        } else if (StringUtils.isEmpty(meter.getMeterDesc())) {
            response.setCode(ErrorCode.EMPTY_MEMBERNAME);
            response.setMsg("empty memberName");
            return response;
        } else if (StringUtils.isEmpty(meter.getRoom())) {
            response.setCode(ErrorCode.EMPTY_ROOM);
            response.setMsg("empty room");
            return response;
        } else if ((!meter.getMemberContact().matches("^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$"))) {
            response.setCode(ErrorCode.INVALID_METERCONTACT);
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
