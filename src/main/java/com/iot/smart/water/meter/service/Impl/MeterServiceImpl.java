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
    private DataService dataService;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public WaterBill getWaterBill(String meterName) {
        Meter meter  = meterMapper.selectMeterByName(meterName);
        if (meter != null) {
            Date date = new Date();
            WaterBill bill = new WaterBill();
            bill.setMeterName(meter.getMeterName());
            Member member = memberMapper.selectMemberById(meter.getMember_id());
            if (member != null) {
                bill.setMemberName(member.getName());
            }
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

//    @Override
//    public List<Meter> getMeters() {
//        return meterMapper.selectAllMeter();
//    }

    @Override
    public List<Meter> getMeterAndMember() {
        return meterMapper.selectMeterAndMember();
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public MeterRequest addMeter(MeterRequest meterRequest) {
        Member member = new Member();
        member.setUser_id(meterRequest.getUser_id());
        member.setCreateDate(new Date());
        member.setContact(meterRequest.getContact());
        member.setName(meterRequest.getName());
        member.setRoom(meterRequest.getRoom());
        memberMapper.insertMember(member);
        Meter meter = new Meter();
        meter.setMember_id(member.getId());
        meter.setMeterName(meterRequest.getMeterName());
        meter.setMeterDesc(meterRequest.getMeterDesc());
        meter.setCreateDate(new Date());
        meterMapper.insertMeter(meter);
        meterRequest.setMember_id(meter.getMember_id());
        return meterRequest;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean updateMeter(MeterRequest meterRequest) {
        Meter meter = new Meter();
        meter.setMeterName(meterRequest.getMeterName());
        meter.setMeterDesc(meterRequest.getMeterDesc());
        meter.setId(meterRequest.getMeter_id());
        meterMapper.updateMeter(meter);
        Member member = new Member();
        member.setRoom(meterRequest.getRoom());
        member.setName(meterRequest.getName());
        member.setContact(meterRequest.getContact());
        member.setId(meterRequest.getMember_id());
        memberMapper.updateMember(member);
        return true;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public boolean deleteMeter(int mid,int bid) {
        volumeMapper.deleteVolumeByMeterId(mid);
        meterMapper.deleteMeterById(mid);
        memberMapper.deleteMemberById(bid);
        return true;
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
