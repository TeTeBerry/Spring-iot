package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Meter;

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

    /**
	 * 
	 * @param mid
	 */
	Meter selectMeterById(@Param("mid") int mid);

    /**
	 * 
	 * @param memberName
	 */
	Meter selectMeterByMemberName(@Param("memberName") String memberName);

    /**
	 * 
	 * @param mid
	 */
	int deleteMeterById(@Param("mid") int mid);

    void createTable();
}
