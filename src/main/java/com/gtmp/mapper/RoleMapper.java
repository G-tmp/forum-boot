package com.gtmp.mapper;


import com.gtmp.POJO.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role selectRoleById(Integer id);

    Role selectRoleByName(String name);

    List<Role> listRoles();

    Role selectRoleWithPermissions(Integer id);
}
