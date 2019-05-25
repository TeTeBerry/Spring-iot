package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.dao.MeterDao;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.response.ErrorCode;
import com.iot.smart.water.meter.response.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeterService {

    @Autowired
    private MeterDao meterDao;

    public Response getMeters() {
        Response<List<Meter>> response = new Response<>();
        List<Meter> meters = meterDao.selectAllMeter();
        response.setData(meters);
        return response;
    }

    public Response addMeter(Meter meter) {
        Response response = new Response();
        try {
            meterDao.insertMeter(meter);
        } catch (Exception e) {

            meterDao.createTable();
            meterDao.insertMeter(meter);
        }
        return response;
    }

    public Response updateMeter(Meter meter) {
        Response response = new Response();
        Meter meterInDB = meterDao.selectMeter(meter.getMeterId());
        if (meterInDB == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterDao.updateMeter(meter);
        }
        return response;
    }

    public Response deleteMeter(String mid) {
        Response response = new Response();
        Meter meter = meterDao.selectMeter(mid);
        if (meter == null) {
            response.setCode(ErrorCode.INVALID_MID);
        } else {
            meterDao.deleteMeter(meter.getMeterId());
        }
        return response;
    }
}
