package com.iot.smart.water.meter.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleMapper {
    String selectRoleNameById(Integer id);

    Integer selectRoleIdByName(String name);
}
