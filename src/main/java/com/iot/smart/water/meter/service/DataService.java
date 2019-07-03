package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.Meter;

import org.springframework.stereotype.Service;

import javafx.util.Pair;

@Service
public interface DataService {

    Pair<Boolean, Boolean> whetherExceedLimit(Meter meter);
}
