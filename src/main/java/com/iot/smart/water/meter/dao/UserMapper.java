package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component("userMapper")
public interface UserMapper {

    int insertUser(User user);

    int updateUser(User user);

	User selectUserByName(@Param("username") String username);

	User selectUserById(@Param("id") Integer id);
}