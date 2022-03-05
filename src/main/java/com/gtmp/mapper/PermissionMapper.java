package com.gtmp.mapper;


import com.gtmp.POJO.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionMapper {

    Permission selectPermissionById(Integer id);

    Permission selectPermissionByOpt(String opt);

    List<Permission> listPermissions();

}
