package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.Data;

import com.iot.smart.water.meter.model.Meter;
import javafx.util.Pair;
import org.springframework.stereotype.Service;



@Service
public interface DataService {

    Pair<Boolean, Boolean> whetherExceedLimit(Meter meter);

    Data getLatestData(String meterName, String start, String end);



}
