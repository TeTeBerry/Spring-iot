package com.iot.smart.water.meter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {
    Integer selectRoleIdByUid(Integer id);

    int insertUidAndRid(@Param("uid") Integer uid, @Param("rid") Integer rid);
}
