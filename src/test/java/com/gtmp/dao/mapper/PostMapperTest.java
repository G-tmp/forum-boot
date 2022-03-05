package com.gtmp.dao.mapper;

import com.gtmp.POJO.Role;
import com.gtmp.POJO.User;
import com.gtmp.mapper.PostMapper;
import com.gtmp.POJO.Post;
import com.gtmp.service.RoleService;
import com.gtmp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest
public class PostMapperTest {


    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;


    @Test
    public void ss(){
        Role role = roleService.selectRoleWithPermissions(2);
        System.out.println(role);
    }


    @Test
    public void asg(){
        User user = userService.selectUserById(2);
        System.out.println(user);
    }
}
