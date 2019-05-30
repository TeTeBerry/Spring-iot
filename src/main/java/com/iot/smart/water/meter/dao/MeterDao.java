package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Meter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MeterDao {

    int insertMeter(Meter meter);

    int updateMeter(Meter meter);

    List<Meter> selectAllMeter();

    Meter selectMeter(@Param("mid") int mid);

    int deleteMeter(@Param("mid") int mid);

    void createTable();

}
