package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.User;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserDao {

    int insertUser(User user);

    int updateUser(User user);

    User selectUser(@Param("userName") String userName, @Param("password") String password);

    User selectUserById(@Param("uid") String uid);

    String selectUserNameForPwd(@Param("userName") String userName);

    void deleteUser(@Param("uid") String uid);

    void createTable();
}
