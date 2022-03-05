package com.gtmp.service;


import com.gtmp.POJO.Permission;
import com.gtmp.POJO.Role;
import com.gtmp.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;


    public Role selectRoleById(Integer id){
        return roleMapper.selectRoleById(id);
    }

    public Role selectRoleByName(String name){
        return roleMapper.selectRoleByName(name);
    }

    public List<Role> listRoles(){
        return roleMapper.listRoles();
    }

    public Role selectRoleWithPermissions(Integer id){
        return roleMapper.selectRoleWithPermissions(id);
    }
}
