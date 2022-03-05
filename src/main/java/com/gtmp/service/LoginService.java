package com.gtmp.service;


import com.gtmp.POJO.User;
import com.gtmp.util.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class LoginService implements ForumConstant {

    @Autowired
    MailClient mailClient;

    @Autowired
    TemplateEngine templateEngine;

    @Autowired
    UserService userService;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    RoleService roleService;

    @Value("${forum.path.domain}")
    private String domain;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${forum.path.upload}")
    private String uploadPath;

    @Value("${spring.mvc.static-path-pattern}")
    private String staticPath;



    public JsonRes register(User user) {
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }

        JsonRes jsonRes = new JsonRes();

        jsonRes.setCode(JsonRes.ERROR_CODE);
        if (user.getUsername() == null) {
            jsonRes.setMsg("账号不能为空!");
            return jsonRes;
        }
        if (user.getPassword() == null) {
            jsonRes.setMsg("密码不能为空!");
            return jsonRes;
        }
        if (user.getEmail() == null) {
            jsonRes.setMsg("邮箱不能为空!");
            return jsonRes;
        }


        // 验证账号
        User u = userService.selectUserByUsername(user.getUsername());
        if (u != null) {
            jsonRes.setMsg("该账号已存在!");
            return jsonRes;
        }

        // 验证邮箱
        u = userService.selectUserByEmail(user.getEmail());
        if (u != null) {
            jsonRes.setMsg("该邮箱已被注册!");
            return jsonRes;
        }

        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
        String password = new SimpleHash("MD5", user.getPassword(), salt, 2).toString();
        user.setSalt(salt);
        user.setPassword(password);
        user.setAvatar(String.format("https://images.nowcoder.com/head/%dt.png", new Random().nextInt(1000)));

        String emailMd5 = ForumUtil.md5(user.getEmail());
        String activeCode = ForumUtil.generateUUID();
        String activeKey = RedisKeyUtil.getActivationKey(emailMd5);

        // 激活邮件
        Context context = new Context();
        context.setVariable("email", user.getEmail());
        // http://localhost:8023/activation/101/code
        String url = domain + ForumUtil.contextPathJudge(contextPath) + "/activation/" + emailMd5 + "/" + activeCode;
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        try {
            mailClient.sendMail(user.getEmail(), "激活账号", content);
        }catch (SendFailedException e){
            jsonRes.setCode(JsonRes.ERROR_CODE).setMsg("Invalid Addresses");
            return jsonRes;
        }catch (MessagingException e) {
            jsonRes.setCode(JsonRes.ERROR_CODE).setMsg("发送邮件失败");
            return jsonRes;
        }

        // 存储 db & redis
        userService.insertUser(user);
        redisTemplate.opsForHash().put(activeKey, "activationCode", activeCode);
        redisTemplate.opsForHash().put(activeKey, "user", user);
        redisTemplate.expire(activeKey, 24, TimeUnit.HOURS);
//        redisTemplate.opsForValue().set(activeKey, user, 2, TimeUnit.HOURS);

        jsonRes.setCode(JsonRes.SUCCESS_CODE);
        jsonRes.setMsg(JsonRes.SUCCESS_MSG);
        return jsonRes;
    }


    public Integer activation(String emailMd5, String code) {
//        User user = userMapper.selectUserById(userId);
        String redisKey = RedisKeyUtil.getActivationKey(emailMd5);
        String activationCode = (String) redisTemplate.opsForHash().get(redisKey, "activationCode");
        User user = (User) redisTemplate.opsForHash().get(redisKey, "user");

        // TODO   redis 激活码过期，重新设置
        if (activationCode == null) {
            // code
            return ACTIVATION_FAILURE;
        }

        if (activationCode == null || code == null || !activationCode.equals(code)) {
            return ACTIVATION_FAILURE;
        } else {
            userService.updateStatus(user.getId(), 1);
            return ACTIVATION_SUCCESS;
        }


//        if (user.getStatus() == 1) {
//            return ACTIVATION_REPEAT;
//        } else if (user.getActivationCode().equals(code)) {
//            userMapper.updateStatus(1, userId);
//            clearCache(userId);
//            return ACTIVATION_SUCCESS;
//        } else {
//            return ACTIVATION_FAILURE;
//        }

    }


    public JsonRes login(String email, String password, String verifyCode, int expiredSecond) {
        JsonRes jsonRes = new JsonRes();

        jsonRes.setCode(JsonRes.ERROR_CODE);
        // cookie中的验证码
        String kaptchaOwner = CookieUtil.getValue(request, "kaptchaOwner");
        if (kaptchaOwner == null) {
            jsonRes.setMsg("flush kaptcha first");
            return jsonRes;
        }

        // 空值处理
        if (StringUtils.isBlank(email)) {
            jsonRes.setMsg("email不能为空!");
            return jsonRes;
        }

        if (StringUtils.isBlank(password)) {
            jsonRes.setMsg("密码不能为空!");
            return jsonRes;
        }


        // ***************
        // redis 中的验证码
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        String kaptcha = (String) redisTemplate.opsForValue().get(redisKey);
        if (StringUtils.isAnyBlank(kaptcha, verifyCode) || !kaptcha.equalsIgnoreCase(verifyCode)) {
            jsonRes.setMsg("验证码错误!");
            return jsonRes;
        }

        // 验证账号
        User user = userService.selectUserByEmail(email);
        if (user == null) {
            jsonRes.setMsg("该email不存在!");
            return jsonRes;
        }

        // 验证激活状态
        if (user.getStatus() == 0) {
            jsonRes.setMsg("该账号未激活!");
            return jsonRes;
        }

//        // 验证密码
//        password = ForumUtil.md5(password + user.getSalt());
//        if (!user.getPassword().equals(password)) {
//            map.put("passwordMsg", "密码不正确!");
//            return map;
//        }

        String salt = user.getSalt();
        password = new SimpleHash("MD5", password, salt, 2).toString();
        System.out.println("password: "+password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(email, password));
            subject.getSession().setAttribute("loginUser", user);
            jsonRes.setData(subject.getSession().getId());

            // 成功登录, 生成登录凭证
//            if (expiredSecond > 0) {
//                LoginTicket loginTicket = new LoginTicket();
//                loginTicket.setUserId(user.getId());
//                loginTicket.setStatus(0);
//                loginTicket.setTicket(ForumUtil.generateUUID());
//                loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSecond * 1000));
//
////            // 设置缓存
////            String redisKeys = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
////            redisTemplate.opsForValue().set(redisKeys, loginTicket, expiredSecond, TimeUnit.SECONDS);
//
//                // 设置 Cookie，response浏览器
//                CookieUtil.add(response, "ticket", loginTicket.getTicket(), contextPath, expiredSecond);
//            }
        }catch (UnknownAccountException|IncorrectCredentialsException  e){
            jsonRes.setMsg("账号密码错误!");
            return jsonRes;
        }

        jsonRes.setCode(JsonRes.SUCCESS_CODE);
        jsonRes.setMsg(JsonRes.SUCCESS_MSG);
        return jsonRes;
    }


//    public void logout() {
//        String ticket = CookieUtil.getValue(request, "ticket");
//        String redisKey = RedisKeyUtil.getTicketKey(ticket);
//        CookieUtil.del(response, "ticket");
////        redisTemplate.delete(redisKey);
//
//        //loginTicketMapper.updateStatus(ticket, 1);
////        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(redisKey);
////        loginTicket.setStatus(1);
////        redisTemplate.opsForValue().set(redisKey, loginTicket);
//    }


//    public LoginTicket findLoginTicket(String ticket) {
//        String redisKey = RedisKeyUtil.getTicketKey(ticket);
//        return (LoginTicket) redisTemplate.opsForValue().get(redisKey);
//    }


}
