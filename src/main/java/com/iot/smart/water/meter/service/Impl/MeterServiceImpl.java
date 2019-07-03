package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.MeterMapper;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.service.MeterService;
import com.iot.smart.water.meter.util.DateUtil;
import com.iot.smart.water.meter.util.EmailUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import javafx.util.Pair;

@Service("meterService")
@EnableScheduling
public class MeterServiceImpl implements MeterService {

    private static final Logger logger = LoggerFactory.getLogger(MeterServiceImpl.class);

    @Autowired
    private MeterMapper meterMapper;

    @Autowired
    private DataService dataService;

    @Autowired
    private EmailUtil emailUtil;

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

    @Scheduled(cron = "0 * * * * ?")
    private void scheduleTask() {
        String currentTime = DateUtil.formatDate(new Date());
        logger.info("execute task in: " + currentTime);
        List<Meter> meters = meterMapper.selectAllMeter();
        if (meters != null) {
            for (Meter meter : meters) {
                if (currentTime.endsWith("000000")) {
                    resetCheck(meter, "01".equals(currentTime.substring(6,8)));
                } else {
                    checkLimit(meter);
                }
            }
        }
    }

    private void checkLimit(Meter meter) {
        Pair<Boolean, Boolean> result = dataService.whetherExceedLimit(meter);
        boolean update = false;
        if (result.getKey()) {
            emailUtil.postEmail(meter.getMemberName(), meter.getMemberContact(), "Today's water exceeds the limit");
            meter.setDailyCheck(1);
            update = true;
        }
        if (result.getValue()) {
            emailUtil.postEmail(meter.getMemberName(), meter.getMemberContact(), "This Month's water exceeds the limit");
            meter.setMonthlyCheck(1);
            update = true;
        }
        if (update) {
            meterMapper.updateMeter(meter);
        }
    }

    private void resetCheck(Meter meter, boolean firstDayOfMonth) {
        boolean update = false;
        if (meter.getDailyCheck() == 1) {
            meter.setDailyCheck(0);
            update = true;
        }
        if (firstDayOfMonth && meter.getMonthlyCheck() == 1) {
            meter.setMonthlyCheck(0);
            update = true;
        }
        if (update) {
            meterMapper.updateMeter(meter);
        }
    }
}
