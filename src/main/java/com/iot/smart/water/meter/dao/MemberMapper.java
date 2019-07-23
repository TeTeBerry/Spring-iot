package com.iot.smart.water.meter.dao;

import com.iot.smart.water.meter.model.Member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface MemberMapper {

    int insertMember(Member member);

    int updateMember(Member member);

    Member selectMemberByName(@Param("name") String name);


    Member selectMemberById(@Param("id") Integer id);
}
