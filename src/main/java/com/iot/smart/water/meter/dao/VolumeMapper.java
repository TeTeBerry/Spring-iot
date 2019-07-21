package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Volume;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VolumeMapper {

    int insertVolume(Volume volume);

    int updateVolume(Volume volume);

    List<Volume> selectAllVolume();

    Volume selectVolumeByMeterId(@Param("meter_id") Integer meter_id);

    Volume selectVolumeByMemberId(@Param("member_id") Integer member_id);

    Volume selectVolumeById(@Param("member_id") Integer member_id, @Param("meter_id") Integer meter_id);
}
