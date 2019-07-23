package com.iot.smart.water.meter.dao;


import com.iot.smart.water.meter.model.Meter;

import com.iot.smart.water.meter.model.MeterRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component("meterMapper")
public interface MeterMapper {

    int insertMeter(Meter meter);

    int updateMeter(Meter meter);

    List<Meter> selectAllMeter();

	Meter selectMeterById(@Param("id") int id);

	int deleteMeterById(@Param("id") int id);

	List<Meter> selectMeterAndMember();

}
