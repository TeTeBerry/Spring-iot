package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.model.Meter;
import com.iot.smart.water.meter.service.DataService;

import com.iot.smart.water.meter.util.DateUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public Data getLatestData(String meterName, String start, String end) {
        return dataMapper.selectLatestDataInMonthByName(meterName, start, end);
    }

    @Override
    public Pair<Boolean, Boolean> whetherExceedLimit(Meter meter) {
        Data data = dataMapper.selectLatestDataByName(meter.getMeterName());
        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);
        if (data != null && DateUtil.isSameDay(data.getReading_time(),ts)) {
            return new Pair<>(data.getTotalMilliters() >= meter.getVolume(),
                    data.getTotalMilliters() >= meter.getVolume() * DateUtil.getDaysOfMonth(new Date()));
        }
        return new Pair<>(false, false);
    }

}
