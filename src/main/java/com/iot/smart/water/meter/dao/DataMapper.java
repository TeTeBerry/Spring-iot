package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;



@Mapper
@Component("dataMapper")
public interface DataMapper {

    /**
	 * 
	 * @param name
	 */
	Data selectLatestDataByName(@Param("name") String name);

    /**
	 * 
	 * @param name
	 * @param startTime
	 * @param endTime
	 */
	Data selectLatestDataInMonthByName(@Param("name") String name,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime);


}
