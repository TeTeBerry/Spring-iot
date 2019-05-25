package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Member;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {

    int insertMember(Member member);

    void createTable();
}
