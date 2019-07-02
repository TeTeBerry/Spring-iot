package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component(value = "userMapper")
public interface UserMapper {

    int insertUser(User user);

    int updateUser(User user);

    User selectUserByName(@Param("userName") String userName);

    User selectUserById(@Param("uid") int uid);

    void createTable();
}