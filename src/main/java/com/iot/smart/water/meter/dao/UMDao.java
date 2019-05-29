package com.iot.smart.water.meter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UMDao {

    int insertUserToMeter(@Param("uid") int uid, @Param("mid") int mid);
}
