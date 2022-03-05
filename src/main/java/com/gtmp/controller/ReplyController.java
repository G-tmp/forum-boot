package com.gtmp.controller;


import com.gtmp.POJO.*;
import com.gtmp.service.PostService;
import com.gtmp.service.ReplyService;
import com.gtmp.util.ForumConstant;
import com.gtmp.util.ForumUtil;
import com.gtmp.util.HostHolder;
import com.gtmp.util.JsonRes;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Map;


@RequestMapping("reply/")
@Controller
public class ReplyController implements ForumConstant {

    @Autowired
    ReplyService replyService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    PostService postService;



    @ResponseBody
    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public JsonRes insert(@RequestBody Map<String, Object> dataMap) {
        String content = (String) dataMap.get("content");
        Integer postId = Integer.valueOf((String) dataMap.get("postId"));

        Session session = SecurityUtils.getSubject().getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if (loginUser == null){

        }
        Reply reply = new Reply()
                .setContent(content)
                .setUserId(loginUser.getId())
                .setPostId(postId)
                .setCreateTime(new Date());
        int i = replyService.insertReply(reply);

//        // 消息队列处理
//        Post post = postService.selectPostById(postId);
//        // 触发评论事件
//        Notify notify = new Notify();
//        notify.setAction(NotifyActionEnum.REPLY.getName())
//                .setObjectType(ObjectTypeEnum.POST.getName())
//                .setObjectId(post.getId())
//                .setCreateTime(new Date())
//                .setContent("")
//                .setFromUserId(loginUser.getId())
//                .setToUserId(post.getUserId());

        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG);
    }

}
