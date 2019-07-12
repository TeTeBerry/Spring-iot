package com.iot.smart.water.meter.service.Impl;
import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.model.WaterBill;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("meterService")
@EnableScheduling
public class MeterServiceImpl implements MeterService {


    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private DataService dataService;




    @Override
    public List<WaterBill> getWaterBill() {
        List<WaterBill> waterBills = null;
        List<Meter> meters = meterMapper.selectAllMeter();
        if (meters != null) {
            Date date = new Date();
            waterBills = new ArrayList<>();
            for (Meter meter : meters) {
                WaterBill bill = new WaterBill();
                bill.setMeterName(meter.getMeterName());
                bill.setMemberName(meter.getMemberName());
                bill.setMonth(DateUtil.getMonth(date));

                Data data = dataService.getLatestData(meter.getMeterName(),
                        DateUtil.getMonthStartTimestamp(date), DateUtil.getMonthEndTimestamp(date));
                if (data != null) {
                    bill.setTotalMilliters(data.getTotalMilliters());
                    bill.setFee(data.getTotalMilliters() / 1000f * 25);
                } else {
                    bill.setFee(0);
                    bill.setTotalMilliters(0);
                }
                waterBills.add(bill);
            }
        }
        return waterBills;
    }

    @Override
    public boolean setMemberVolume(String memberName, float volume) {
        Meter meter = meterMapper.selectMeterByMemberName(memberName);
        if (meter == null || volume <= 0) {
            return false;
        }
        meter.setVolume(volume);
        meterMapper.updateMeter(meter);
        return true;
    }

    @Override
    public List<Meter> getMeters() {
        return meterMapper.selectAllMeter();
    }

    @Override
    public Meter addMeter(Meter meter) {
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
