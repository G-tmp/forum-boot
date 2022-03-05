package com.gtmp.service;

import com.gtmp.POJO.Role;
import com.gtmp.mapper.RoleMapper;
import com.gtmp.mapper.UserMapper;
import com.gtmp.POJO.User;
import com.gtmp.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class UserService implements ForumConstant {

    @Resource
    UserMapper userMapper;

    @Autowired
    RoleMapper roleMapper;

    @Resource
    RedisTemplate redisTemplate;

    //@Resource
    //LoginTicketMapper loginTicketMapper;

    @Value("${forum.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${forum.path.upload}")
    private String uploadPath;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPath;



    /**
     * 1. 优先从缓存中取值。
     * 2. 取不到，则初始化缓存.
     * 3. 数据变更时，清除缓存数据。
     */


    /**
     * 1. 优先从缓存中取值。
     *
     * @param userId
     * @return
     */
    private User getCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(redisKey);
    }

    /**
     * 2. 取不到，则初始化缓存.
     *
     * @param userId
     * @return
     */
    private User fillCache(int userId) {
        User user = userMapper.selectUserById(userId);
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.opsForValue().set(redisKey, user, 10 * 60, TimeUnit.SECONDS);
        return user;
    }

    /**
     * 3. 数据变更时，清除缓存数据。
     *
     * @param userId
     */
    private void clearCache(int userId) {
        String redisKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(redisKey);
    }


    public User selectUserById(int id) {
        User user = getCache(id);
        if (user == null) {
            user = fillCache(id);
        }
        return user;
    }


    public User selectUserByEmail(String email){
        return userMapper.selectUserByEmail(email);
    }


    public User selectUserByUsername(String name) {
        return userMapper.selectUserByUsername(name);
    }


    public JsonRes updateAvatar(Integer userId, MultipartFile img) {
//        String path = request.getServletContext().getRealPath("resources/img_profile/");

        String result = null;
        JsonRes jsonRes = new JsonRes();

        try {
            result = UploadUtil.upload(img, uploadPath);
        } catch (IOException e) {
            e.printStackTrace();
            jsonRes.setCode(JsonRes.ERROR_CODE).setMsg("server error");
            return jsonRes;
        }

        if (result == null) {
            jsonRes.setCode(JsonRes.ERROR_CODE).setMsg("server error");
            return jsonRes;
        }

        System.out.println(staticPath);
        result = "/static/" + result;
        userMapper.updateAvatar(userId, result);
        clearCache(userId);

//        String a = request.getScheme() + "://" + request.getServerName()
//                + ":" + request.getServerPort() + result;

        System.out.println(uploadPath + "\t" + result);
        Map<String,String> map= new LinkedHashMap<>();
        map.put("path", result);
        jsonRes.setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG).setData(map);
        return jsonRes;
    }

    public User selectUserWithRoleByEmail(String email) {
        User user = userMapper.selectUserByEmail(email);
        if (user == null)
            return null;

        Role role = roleMapper.selectRoleWithPermissions(user.getRoleId());
        user.setRole(role);
        return user;
    }


    public User selectUserWithRoleByUsername(String username) {
        User user = userMapper.selectUserByUsername(username);
        if (user == null)
            return null;

        Role role = roleMapper.selectRoleWithPermissions(user.getRoleId());
        user.setRole(role);
        return user;
    }

    public Integer insertUser(User user){
        return userMapper.insertUser(user);
    }

    public Integer updatePassword(Integer id, String password){
        return userMapper.updatePassword(id, password);
    }

    public Integer updateStatus(Integer id, Integer status){
        return userMapper.updateStatus(id, status);
    }
}