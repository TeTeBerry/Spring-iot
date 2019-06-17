package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    int insertUser(User user);

    int updateUser(User user);

    User selectUserByName(@Param("userName") String userName);

    User selectUserById(@Param("uid") int uid);

    void createTable();
}