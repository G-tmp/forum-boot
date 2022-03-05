package com.gtmp.mapper;


import com.gtmp.POJO.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {


    User selectUserById(int id);

    User selectUserByUsername(String username);

    User selectUserByEmail(String email);

    Integer insertUser(User user);

    Integer updateStatus(Integer id, Integer status);

    Integer updateAvatar(Integer id, String avatar);

    Integer updatePassword(Integer id, String password);
}
