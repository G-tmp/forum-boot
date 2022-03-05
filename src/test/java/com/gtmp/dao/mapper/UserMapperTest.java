package com.gtmp.dao.mapper;

import com.gtmp.mapper.UserMapper;
import com.gtmp.POJO.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserMapperTest {
    @Resource
    UserMapper userMapper;


    @Test
    void selectUserById() {
        userMapper.selectUserById(154);
    }

    @Test
    void selectByName() {

    }
}