package com.gtmp.controller;


import com.gtmp.POJO.User;
import com.gtmp.service.NotificationService;
import com.gtmp.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/notification")
public class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;



    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(){
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();

        notificationService.listNotificationByUserId(user.getId());

        return "";
    }

}
