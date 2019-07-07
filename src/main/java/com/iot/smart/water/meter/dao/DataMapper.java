package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DataMapper {

    Data selectLatestDataByName(@Param("name") String name);

    Data selectLatestDataInMonthByName(@Param("name") String name,
                                       @Param("startTime") long startTime,
                                       @Param("endTime") long endTime);
}
