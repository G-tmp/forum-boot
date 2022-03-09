package com.gtmp.controller;

import com.gtmp.POJO.User;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.LikeService;
import com.gtmp.service.UserService;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.JsonRes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;


@Controller
public class UserController implements ForumConstant {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Value("${forum.path.domain}")
    private String domain;

    @Value("${forum.path.upload}")
    private String uploadPath;

    //  is /
    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Resource
    UserService userService;

    @Resource
    LikeService likeService;



    @RequestMapping(value = "/user/setting", method = RequestMethod.GET)
    public String toSettingPage() {
        return "/site/setting";
    }


    @ResponseBody
    @RequestMapping(value = "/user/upload", method = RequestMethod.POST)
    public JsonRes uploadAvatar(@RequestParam("img") MultipartFile avatarImage) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("loginUser");

        return userService.updateAvatar(user.getId(), avatarImage);

        // 上传成功，更新当前用户的头像路径(web访问路径)
        // http://localhost:8023/user/header/xxx.png

//        String avatar = domain + ForumUtil.contextPathJudge(contextPath) + "/user/avatar/" + map.get("path");

    }

    @RequestMapping(value = "/user/avatar/{fileName}", method = RequestMethod.GET)
    public void getAvatar(@PathVariable("fileName") String fileName, HttpServletResponse response) {
        // 文件存储路径
        fileName = uploadPath + "/" + fileName;
        // 文件后缀类型
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        // 响应图片
        response.setContentType("image/" + suffix);
        try (OutputStream os = response.getOutputStream(); FileInputStream fis = new FileInputStream(fileName)) {
            byte[] buffer = new byte[1024];
            int b = 0;
            while ((b = fis.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        } catch (IOException e) {
            logger.error("读取头像文件失败: " + e.getMessage());
        }
    }


    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String getProfilePage(Model model) {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        System.out.println(user);

        if (user == null) {
            throw new RuntimeException("error");
        }

        // 用户
        // 获赞数量
//        int likeCount = likeService.findUserLikeCount(user.getId());
//        model.addAttribute("likeCount", likeCount);
//
        // 关注数量
//        long followingCount = followService.findFollowingCount(ObjectTypeEnum.USER, user.getId());
//        // 粉丝数量
//        long followerCount = followService.findFollowerCount(ObjectTypeEnum.USER, user.getId());

        model.addAttribute("user", user);
//        model.addAttribute("followingCount", followingCount);
//        model.addAttribute("followerCount", followerCount);

        return "bro/profile";
    }


    @RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
    public String getUserPage(@PathVariable("username") String username, Model model) {
        User user = userService.selectUserByUsername(username);
        if (user == null) {
           return "error/404";
        }

//        // 获赞数量
//        int likeCount = likeService.findUserLikeCount(userId);
//        model.addAttribute("likeCount", likeCount);
//
        // 关注数量
//        long followingCount = followService.findFollowingCount(ObjectTypeEnum.USER, user.getId());
//        // 粉丝数量
//        long followerCount = followService.findFollowerCount(ObjectTypeEnum.USER, user.getId());
//        // 是否当前用户已关注此用户
//        boolean isFollowing = false;
//        if (hostHolder.getUser() != null) {
//            isFollowing = followService.hasFollowed(hostHolder.getUser().getId(), ObjectTypeEnum.USER, user.getId());
//        }

//        model.addAttribute("followerCount", followerCount);
//        model.addAttribute("followingCount", followingCount);
//        model.addAttribute("ifFollowing", isFollowing);
        model.addAttribute("user", user);

        return "bro/user";
    }
}
