package com.gtmp.controller;


import com.gtmp.POJO.*;
import com.gtmp.service.NotificationService;
import com.gtmp.service.PostService;
import com.gtmp.service.ReplyService;
import com.gtmp.service.UserService;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.JsonRes;
import com.gtmp.util.RegexUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.Map;
import java.util.Set;


@RequestMapping("reply/")
@Controller
public class ReplyController implements ForumConstant {

    @Autowired
    ReplyService replyService;

    @Autowired
    PostService postService;

    @Autowired
    UserService userService;

    @Autowired
    NotificationService notificationService;


    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public JsonRes insert(@RequestBody Map<String, Object> dataMap) {
        String content = (String) dataMap.get("content");
        Integer postId = Integer.valueOf((String) dataMap.get("postId"));

        Session session = SecurityUtils.getSubject().getSession();
        User loginUser = (User) session.getAttribute("loginUser");

        content = HtmlUtils.htmlEscape(content);

        Reply reply = new Reply()
                .setContent(content)
                .setUserId(loginUser.getId())
                .setPostId(postId)
                .setCreateTime(new Date());
        int replyId = replyService.insertReply(reply);

        Post post = postService.selectPostById(postId);
        Integer toUserId = post.getUserId();

        // notify post owner
        if (!toUserId.equals(loginUser.getId())) {
            Notification notification = new Notification();
            notification
                    .setFromUserId(loginUser.getId())
                    .setToUserId(toUserId)
                    .setEvent(Notification.Event.REPLY.toString())
                    .setTriggerType(Notification.Trigger.POST.toString())
                    .setTriggerId(postId);
            int i = notificationService.insertNotification(notification);
            // websocket
        }

        // notify @user
        Set<String> mention = RegexUtil.mention(content);
        if (mention != null){
            for (String username:mention){
                User mentionUser = userService.selectUserByUsername(username);
                if (mentionUser != null  && !mentionUser.getId().equals(loginUser)){
                    Notification notification = new Notification();
                    notification
                            .setFromUserId(loginUser.getId())
                            .setToUserId(mentionUser.getId())
                            .setEvent(Notification.Event.MENTION.toString())
                            .setTriggerType(Notification.Trigger.REPLY.toString())
                            .setTriggerId(replyId);
                    int i = notificationService.insertNotification(notification);
                    // websocket
                }
            }
        }

        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG);
    }

}
