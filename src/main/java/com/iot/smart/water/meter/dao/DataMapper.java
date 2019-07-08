package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component(value = "dataMapper")
public interface DataMapper {

    Data selectLatestDataByName(@Param("name") String name);

    Data selectLatestDataInMonthByName(@Param("name") String name,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime);

    List<Data> selectAllSensorData();

}
