package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.MeterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("meterService")
public class MeterServiceImpl implements MeterService {

    @Autowired
    private MeterMapper meterMapper;


    @Override
    public List<Meter> getMeters() {
        return meterMapper.selectAllMeter();
    }

    @Override
    public  Meter addMeter(Meter meter) {
        meter.setCreateDate(new Date());
        try {
            meterMapper.insertMeter(meter);
        } catch (Exception e) {
            meterMapper.createTable();
            meterMapper.insertMeter(meter);
        }
        return meter;
    }

    @Override
    public Meter updateMeter(Meter meter) {

        return meterMapper.selectMeterById(meter.getMid());
    }

    @Override
    public Meter deleteMeter(int mid) {

        return meterMapper.selectMeterById(mid);
    }

}
