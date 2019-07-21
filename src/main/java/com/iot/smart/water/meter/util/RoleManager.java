package com.iot.smart.water.meter.util;

import com.iot.smart.water.meter.dao.RoleMapper;
import com.iot.smart.water.meter.dao.UserRoleMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleManager {

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_MEMBER = "ROLE_MEMBER";

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    public boolean isAdmin(Integer id) {
        String role = roleMapper.selectRoleNameById(userRoleMapper.selectRoleIdByUid(id));
        return ROLE_ADMIN.equals(role);
    }

    public boolean isMember(Integer id) {
        String role = roleMapper.selectRoleNameById(userRoleMapper.selectRoleIdByUid(id));
        return ROLE_MEMBER.equals(role);
    }
}
