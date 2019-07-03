package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.DataService;
import com.iot.smart.water.meter.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import javafx.util.Pair;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public Pair<Boolean, Boolean> whetherExceedLimit(Meter meter) {
        Data data = dataMapper.selectLatestDataByName(meter.getMeterName());
        if (data != null && DateUtil.isSameDay(data.getReadingTime(), System.currentTimeMillis())) {
            return new Pair<>(data.getTotalMilliLtres() >= meter.getVolume(),
                    data.getTotalMilliLtres() >= meter.getVolume() * DateUtil.getDaysOfMonth(new Date()));
        }
        return new Pair<>(false, false);
    }
}
