package com.iot.smart.water.meter.service.Impl;

import com.iot.smart.water.meter.dao.DataMapper;
import com.iot.smart.water.meter.model.Data;
import com.iot.smart.water.meter.service.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private DataMapper dataMapper;

    @Override
    public Data getLatestData(String meterName, String start, String end) {
        return dataMapper.selectLatestDataInMonthByName(meterName, start, end);
    }

//    @Override
//    public Pair<Boolean, Boolean> whetherExceedLimit(Meter meter) {
//        Data data = dataMapper.selectLatestDataByName(meter.getMeterName());
//        if (data != null && DateUtil.isSameDay(data.getReadingTime(), System.currentTimeMillis())) {
//            return new Pair<>(data.getTotalMilliters() >= meter.getVolume(),
//                    data.getTotalMilliters() >= meter.getVolume() * DateUtil.getDaysOfMonth(new Date()));
//        }
//        return new Pair<>(false, false);
//    }
    @Override
    public List<Data> getSensorData() {
        return dataMapper.selectAllSensorData();
    }

}
