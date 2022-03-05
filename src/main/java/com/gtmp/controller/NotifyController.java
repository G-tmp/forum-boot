package com.gtmp.controller;


import com.gtmp.POJO.Notify;
import com.gtmp.POJO.Post;
import com.gtmp.POJO.User;
import com.gtmp.enums.NotifyActionEnum;
import com.gtmp.enums.ObjectTypeEnum;
import com.gtmp.service.NotifyService;
import com.gtmp.service.PostService;
import com.gtmp.service.UserService;
import com.gtmp.util.HostHolder;
import com.gtmp.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class NotifyController {


    @Autowired
    NotifyService notifyService;

    @Autowired
    PostService postService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;


    @RequestMapping(value = "notify/list", method = RequestMethod.GET)
    public String list(Model model) {
        User loginUser = hostHolder.getUser();

        // reply notify
        Notify replyNotify = notifyService.selectNotifyRecent(loginUser.getId(), NotifyActionEnum.REPLY.getName());
        Map replyMap = new HashMap<>();
        if (replyNotify != null) {
            replyMap.put("notify", replyNotify);

            User user = userService.selectUserById(replyNotify.getFromUserId());
            replyMap.put("user", user);

            Post post = postService.selectPostById(replyNotify.getObjectId());
            replyMap.put("object", post);

            int unread = notifyService.selectNotifyCountUnread(loginUser.getId(), NotifyActionEnum.REPLY.getName());
            replyMap.put("unread", unread);

        }
        model.addAttribute("replyMap", replyMap);


        // like notify
//        Notify likeNotify = notifyService.selectNotifyRecent(loginUser.getId(), NotifyActionEnum.LIKE.getName());
//        Map likeMap = new HashMap<>();
//        if (replyNotify != null) {
//            likeMap.put("notify",replyNotify);
//
//            User user = userService.selectUserById(replyNotify.getFromUserId());
//            likeMap.put("user",user);
//
//            Post post = postService.selectPostById(replyNotify.getObjectId());
//            likeMap.put("object",post);
//        }
//        model.addAttribute("likeNotify", likeMap);
//
//
//        // follow notify
//        Notify followNotify = notifyService.selectNotifyRecent(loginUser.getId(), NotifyActionEnum.FOLLOW.getName());
//        Map followMap = new HashMap<>();
//        if (replyNotify != null) {
//            likeMap.put("notify",replyNotify);
//
//            User user = userService.selectUserById(replyNotify.getFromUserId());
//            likeMap.put("user",user);
//
//            Post post = postService.selectPostById(replyNotify.getObjectId());
//            likeMap.put("object",post);
//        }
//        model.addAttribute("followNotify", followMap);


        return "bro/notice";
    }


    @RequestMapping(value = "/notify/{action}", method = RequestMethod.GET)
    public String action(Model model, @PathVariable(value = "action") String action, @RequestParam(value = "page", required = false) Integer cur) {
        User loginUser = hostHolder.getUser();
        Page page = new Page();
        page.setProperties(5, cur, notifyService.selectNotifyCountUnread(loginUser.getId(), action));
        page.setPath("/notify/list/" + action);

        List<Notify> notifies = notifyService.listAllNotifyUnread(loginUser.getId(), action, (page.getCur() - 1) * page.getSize(), page.getSize());
        List<Map<String, Object>> notifyList = new ArrayList<>();
        if (notifies != null) {
            for (Notify notify : notifies) {
                Map<String,Object> map = new HashMap<>();
                map.put("notify", notify);

                User user = userService.selectUserById(notify.getFromUserId());
                map.put("user", user);

                Post post = postService.selectPostById(notify.getObjectId());
                map.put("object", post);

                notifyList.add(map);
            }
            System.out.println(notifyList.size());
        }

        model.addAttribute("notifyList", notifyList);
        model.addAttribute("page", page);

        notifyService.updateStatus(loginUser.getId(),1,NotifyActionEnum.REPLY.getName(), ObjectTypeEnum.POST.getName());

        return "bro/notice-detail";
    }
}
