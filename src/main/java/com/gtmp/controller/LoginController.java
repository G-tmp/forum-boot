package com.gtmp.controller;


import com.google.code.kaptcha.Producer;
import com.gtmp.POJO.User;
import com.gtmp.service.LoginService;
import com.gtmp.util.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Controller
public class LoginController implements ForumConstant {


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;

    @Resource
    private Producer kaptchaProducer;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource
    RedisTemplate redisTemplate;


    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String toRegisterPage() {
        return "bro/register";
    }


    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public JsonRes register( @RequestBody User user) {
        System.out.println(user);
        JsonRes jsonRes = loginService.register(user);
        return jsonRes;
    }


    /**
     * 账号激活
     * http://127.0.0.1:8080/activation/{emailMd5}/{code}
     */
    @RequestMapping("/activation/{emailMd5}/{code}")
    public String activation(Model model, @PathVariable("emailMd5") String emailMd5, @PathVariable("code") String code) {
        int result = loginService.activation(emailMd5, code);
        switch (result) {
            case ACTIVATION_SUCCESS:
                model.addAttribute("msg", "激活成功,您的账号已经可以正常使用了!");
                model.addAttribute("target", "/login");
                logger.info("active success");
                break;
            case ACTIVATION_REPEAT:
                model.addAttribute("msg", "重复操作,您的账号已经激活过了!");
                model.addAttribute("target", "/index");
                logger.info("active repeat");
                break;
            default:
                model.addAttribute("msg", "激活失败,请检查您的激活码是否正确!");
                model.addAttribute("target", "/index");
                logger.info("active faild");
                break;
        }
        return "/site/operate-result";
    }


    /**
     *  generate kaptcha code
     * @param response
     */
    @RequestMapping(value = "/kaptcha", method = RequestMethod.GET)
    public void getKaptcha(HttpServletResponse response) {
        String text = kaptchaProducer.createText();
        BufferedImage image = kaptchaProducer.createImage(text);

        int expired = 60 * 2;
        // 验证码 存入 Cookie
        String kaptchaOwner = ForumUtil.generateUUID();
        CookieUtil.add(response, "kaptchaOwner", kaptchaOwner, contextPath, expired);
        response.setContentType("image/png");

        // 将验证码存入 redis
        String redisKey = RedisKeyUtil.getKaptchaKey(kaptchaOwner);
        redisTemplate.opsForValue().set(redisKey, text, expired, TimeUnit.SECONDS);

        // 将图片输出给浏览器
        try (ServletOutputStream os = response.getOutputStream()) {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            logger.error("响应验证码失败" + e.getMessage());
        }
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String toLoginPage() {
        return "bro/login";
    }


    /**
     * @CookieValue 从浏览器获取
     * 对于 普通参数类型，不会自动封装到 Model 对象中，所以模板引擎中无法取到，
     * 可以使用 request 域对象取值
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public JsonRes login(@RequestBody Map<String, Object> dataMap) {
        String email = (String) dataMap.get("email");
        String password = (String) dataMap.get("password");
        String verifyCode = (String) dataMap.get("verifyCode");
        boolean rememberMe = (boolean) dataMap.get("rememberMe");

//        //检查账号、密码、失效时间 判断登录
//        int expiredSeconds = rememberMe ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;

        return loginService.login(email, password, verifyCode, rememberMe);
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/";
    }

}
