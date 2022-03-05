package com.gtmp.service;


import com.gtmp.POJO.Permission;
import com.gtmp.mapper.PermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;


    public Permission selectPermissionById(Integer id){
        return permissionMapper.selectPermissionById(id);
    }

    public Permission selectPermissionByOpt(String opt){
        return permissionMapper.selectPermissionByOpt(opt);
    }

    public List<Permission> listPermissions(){
        return permissionMapper.listPermissions();
    }
}
