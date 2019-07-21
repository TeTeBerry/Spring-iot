package com.iot.smart.water.meter.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface RoleMapper {
    String selectRoleNameById(Integer id);

    Integer selectRoleIdByName(String name);
}
