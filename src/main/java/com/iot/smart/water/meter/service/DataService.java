package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.Data;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataService {

//    Pair<Boolean, Boolean> whetherExceedLimit(Meter meter);

    Data getLatestData(String meterName, String start, String end);

    List<Data> getSensorData();


}
