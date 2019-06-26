package com.iot.smart.water.meter.service;

import com.iot.smart.water.meter.model.Meter;



import java.util.List;

public interface MeterService {


List<Meter> getMeters();

Meter addMeter(Meter meter);

Meter updateMeter(Meter meter);

Meter deleteMeter(int mid);

}
