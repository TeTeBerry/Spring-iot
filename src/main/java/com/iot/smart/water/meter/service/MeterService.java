package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.dao.UMDao;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.User;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterService {

    @Autowired
    private UMDao umDao;

    @Autowired
    private MeterDao meterDao;

    public Response getMeters() {
        Response<List<Meter>> response = new Response<>();
        List<Meter> meters = meterDao.selectAllMeter();
        response.setData(meters);
        return response;
    }

    public Response addMeter(User user, Meter meter) {
        Response response = new Response();
        meterDao.insertMeter(meter);
        umDao.insertUserToMeter(user.getUid(), meter.getMid());
        response.setMsg("add meter success");
        return response;
    }

    public Response updateMeter(Meter meter) {
        Response response = new Response();
        Meter meterInDB = meterDao.selectMeter(meter.getMid());
        if (meterInDB == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterDao.updateMeter(meter);
        }
        return response;
    }

    public Response deleteMeter(int mid) {
        Response response = new Response();
        Meter meter = meterDao.selectMeter(mid);
        if (meter == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterDao.deleteMeter(meter.getMid());
        }
        return response;
    }
}
