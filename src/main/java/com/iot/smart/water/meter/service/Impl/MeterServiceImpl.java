package com.iot.smart.water.meter.service.Impl;
import com.iot.smart.water.meter.dao.*;
import com.iot.smart.water.meter.model.*;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("meterService")
@EnableScheduling
public class MeterServiceImpl implements MeterService {

    private static final Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private VolumeMapper volumeMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private DataService dataService;


    @Autowired
    private MemberMapper memberMapper;

    @Override
    public WaterBill getWaterBill(String meterName) {
        List<Meter> meters = meterMapper.selectAllMeter();
        if (meters != null) {
            Date date = new Date();
            for (Meter meter : meters) {
                WaterBill bill = new WaterBill();
                bill.setMeterName(meter.getMeterName());
//                bill.setMemberName(meter.getMemberName());
                bill.setMonth(DateUtil.getMonth(date));

                Data data = dataService.getLatestData(meter.getMeterName(),
                        DateUtil.getMonthStartTimestamp(date), DateUtil.getMonthEndTimestamp(date));
                if (data != null) {
                    bill.setTotalMilliters(data.getTotalMilliters());
                    bill.setFee(data.getTotalMilliters() / 1000 * 25);
                } else {
                    bill.setFee(0);
                    bill.setTotalMilliters(0);
                }
                return bill;

            }
        }
        return null;

    }

    @Override
    public boolean setMemberVolume(Volume volume, long newVolumeNum) {
        if (volume == null || volume.getChangeLimit() == 1) {
            return false;
        }
        volume.setVolume(newVolumeNum);
        volume.setChangeLimit(1);
        if (volume.getId() == null) {
            volumeMapper.insertVolume(volume);
        } else {
            volumeMapper.updateVolume(volume);
        }
        return true;
    }

    @Override
    public List<Meter> getMeters() {
        return meterMapper.selectAllMeter();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Meter addMeter(Meter meter) {
        meter.setCreateDate(new Date());
        meterMapper.insertMeter(meter);
        return meter;
    }

    @Override
    public Meter updateMeter(Meter meter) {
        return meterMapper.selectMeterById(meter.getId());
    }

    @Override
    public Meter deleteMeter(int mid) {
        return meterMapper.selectMeterById(mid);
    }

    @Scheduled(cron = "0 0 0 ? * MON")
    public void scheduleTask() {
        logger.info("MeterServiceImpl schedule task");
        List<Volume> volumes = volumeMapper.selectAllVolume();
        if (volumes != null) {
            for (Volume volume : volumes) {
                volume.setChangeLimit(0);
                volume.setNotifyLimit(0);
                volumeMapper.updateVolume(volume);
            }
        }
    }
}
