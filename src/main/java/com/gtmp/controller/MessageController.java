package com.gtmp.controller;


import com.alibaba.fastjson.JSONObject;
import com.gtmp.POJO.Message;
import com.gtmp.POJO.User;
import com.gtmp.service.MessageService;
import com.gtmp.service.UserService;
import com.gtmp.util.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import java.util.*;

@Controller
public class MessageController implements ForumConstant {

    @Autowired
    MessageService messageService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;


    /**
     * 私信列表
     */
    @RequestMapping(value = "/letter/list", method = RequestMethod.GET)
    public String getLetterList(Model model, @RequestParam(value = "page", required = false) Integer cur) {
        User loginUser = hostHolder.getUser();
        // 设置分页信息
        Page page = new Page();
        page.setSize(5);
        page.setPath("/letter/list");
        page.setProperties(5, cur, messageService.selectConversationCount(loginUser.getId()));

        // 会话列表
        List<Message> conversationList = messageService.selectConversations(loginUser.getId(), (page.getCur() - 1) * page.getSize(), page.getSize());
        List<Map<String, Object>> conversations = new ArrayList<>();
        if (conversationList != null) {
            for (Message message : conversationList) {
                Map<String, Object> map = new HashMap<>();
                map.put("conversation", message);
                map.put("letterCount", messageService.selectLetterCount(message.getConversationType()));
                map.put("unreadCount", messageService.selectLetterUnreadCount(loginUser.getId(), message.getConversationType()));
                int targetId = loginUser.getId().equals(message.getFromUserId()) ? message.getToUserId() : message.getFromUserId();
                map.put("target", userService.selectUserById(targetId));
                conversations.add(map);
            }
        }

        // 查询私信消息数量
        Integer letterUnreadCount = messageService.selectLetterUnreadCount(loginUser.getId(), null);
        // 查询通知消息数量
        Integer noticeUnreadCount = messageService.selectNoticeUnreadCount(loginUser.getId(), null);

        model.addAttribute("noticeUnreadCount", noticeUnreadCount);
        model.addAttribute("letterUnreadCount", letterUnreadCount);
        model.addAttribute("conversations", conversations);
        model.addAttribute("page", page);

        return "bro/letter";
    }

    @RequestMapping(value = "/letter/detail/{conversationType}", method = RequestMethod.GET)
    public String getLetterDetail(@PathVariable("conversationType") String conversationType, Page page, Model model) {
        // 分页信息
        page.setSize(5);
        page.setPath("/letter/detail/" + conversationType);
        page.setTotalRecord(messageService.selectLetterCount(conversationType));

        // 私信列表
        List<Message> letterList = messageService.selectLetters(conversationType, 0, page.getSize());
        List<Map<String, Object>> letters = new ArrayList<>();
        if (letterList != null) {
            for (Message message : letterList) {
                Map<String, Object> map = new HashMap<>();
                map.put("letter", message);
                map.put("fromUser", userService.selectUserById(message.getFromUserId()));
                letters.add(map);
            }
        }
        model.addAttribute("letters", letters);

        // 私信目标
        model.addAttribute("target", getLetterTarget(conversationType));

        //设置消息为已读
        List<Integer> ids = getLetterIds(letterList);
        if (!ids.isEmpty()) {
            messageService.readMessage(ids);
        }

        return "site/letter-detail";
    }



    private User getLetterTarget(String conversationType) {
        String[] ids = conversationType.split("_");
        User user0 = userService.selectUserById(Integer.parseInt(ids[0]));
        User user1 = userService.selectUserById(Integer.parseInt(ids[1]));
        return hostHolder.getUser().equals(user0) ? user1 : user0;
    }

    @RequestMapping(value = "/letter/send", method = RequestMethod.POST)
    @ResponseBody
    public JsonRes sentLetter(String toName, String content) {
        User target = userService.selectUserByUsername(toName);
        if (target == null) {
            return new JsonRes().setCode(JsonRes.ERROR_CODE).setMsg("目标用户不存在!");
        }

        Message message = new Message();
        message.setFromUserId(hostHolder.getUser().getId());
        message.setToUserId(target.getId());
        if (message.getFromUserId() < message.getToUserId()) {
            message.setConversationType(message.getFromUserId() + "_" + message.getToUserId());
        } else {
            message.setConversationType(message.getToUserId() + "_" + message.getFromUserId());
        }
        message.setContent(content);
        message.setStatus(0);
        message.setCreateTime(new Date());
        messageService.insertMessage(message);

        return new JsonRes().setCode(JsonRes.SUCCESS_CODE).setMsg(JsonRes.SUCCESS_MSG);
    }


    @RequestMapping(value = "/notice/list", method = RequestMethod.GET)
    public String getNoticeList(Model model) {
        User loginUser = hostHolder.getUser();

        // 查询评论类的通知
        Message message = messageService.selectLatestNotice(loginUser.getId(), QUEUE_REPLY);
        Map<String, Object> replyMessage = new HashMap<>();
        if (message != null) {
            replyMessage.put("message", message);

            String content = HtmlUtils.htmlUnescape(message.getContent());
            Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);

            replyMessage.put("user", userService.selectUserById((Integer) data.get("userId")));
            replyMessage.put("entityType", data.get("entityType"));
            replyMessage.put("entityId", data.get("entityId"));
            replyMessage.put("postId", data.get("postId"));

            int count = messageService.selectNoticeCount(loginUser.getId(), QUEUE_REPLY);
            replyMessage.put("count", count);
            int unread = messageService.selectNoticeUnreadCount(loginUser.getId(), QUEUE_REPLY);
            replyMessage.put("unread", unread);
        }
        model.addAttribute("replyNotice", replyMessage);


        // 查询点赞类的通知
        message = messageService.selectLatestNotice(loginUser.getId(), QUEUE_LIKE);
        Map likeMessage = new HashMap<>();
        if (message != null) {
            likeMessage.put("message", message);

            String content = HtmlUtils.htmlUnescape(message.getContent());
            Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);

            likeMessage.put("user", userService.selectUserById((Integer) data.get("userId")));
            likeMessage.put("entityType", data.get("entityType"));
            likeMessage.put("entityId", data.get("entityId"));
            likeMessage.put("postId", data.get("postId"));

            int count = messageService.selectNoticeCount(loginUser.getId(), QUEUE_LIKE);
            likeMessage.put("count", count);
            int unread = messageService.selectNoticeUnreadCount(loginUser.getId(), QUEUE_LIKE);
            likeMessage.put("unread", unread);
        }
        model.addAttribute("likeNotice", likeMessage);


        // 查询关注类的通知
        message = messageService.selectLatestNotice(loginUser.getId(), QUEUE_FOLLOW);
        Map followMessage = new HashMap<>();
        if (message != null) {
            followMessage.put("message", message);

            String content = HtmlUtils.htmlUnescape(message.getContent());
            Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);

            followMessage.put("user", userService.selectUserById((Integer) data.get("userId")));
            followMessage.put("entityType", data.get("entityType"));
            followMessage.put("entityId", data.get("entityId"));

            int count = messageService.selectNoticeCount(loginUser.getId(), QUEUE_FOLLOW);
            followMessage.put("count", count);
            int unread = messageService.selectNoticeUnreadCount(loginUser.getId(), QUEUE_FOLLOW);
            followMessage.put("unread", unread);
        }
        model.addAttribute("followNotice", followMessage);

        // 查询未读消息数量
        int letterUnreadCount = messageService.selectLetterUnreadCount(loginUser.getId(), null);
        model.addAttribute("letterUnreadCount", letterUnreadCount);

        int noticeUnreadCount = messageService.selectNoticeUnreadCount(loginUser.getId(), null);
        model.addAttribute("noticeUnreadCount", noticeUnreadCount);

        return "bro/notice";
    }


    @RequestMapping(value = "/notice/detail/{topic}", method = RequestMethod.GET)
    public String getNoticeDetail(@PathVariable("topic") String topic, @RequestParam(value = "page", required = false) Integer cur, Model model) {
        User loginUser = hostHolder.getUser();

        Page page = new Page();
        page.setPath("/notice/detail/" + topic);
        page.setProperties(5, cur, messageService.selectNoticeCount(loginUser.getId(), topic));

        List<Message> noticeList = messageService.selectNotices(loginUser.getId(), topic, (page.getCur() - 1) * page.getSize(), page.getSize());
        List<Map<String, Object>> noticeVoList = new ArrayList<>();
        if (noticeList != null) {
            for (Message notice : noticeList) {
                Map<String, Object> map = new HashMap<>();
                // 通知
                map.put("notice", notice);
                // 内容
                String content = HtmlUtils.htmlUnescape(notice.getContent());
                Map<String, Object> data = JSONObject.parseObject(content, HashMap.class);
                map.put("user", userService.selectUserById((Integer) data.get("userId")));
                map.put("entityType", data.get("entityType"));
                map.put("entityId", data.get("entityId"));
                map.put("postId", data.get("postId"));
                // 通知的作者
                map.put("fromUser", userService.selectUserById(notice.getFromUserId()));

                noticeVoList.add(map);
            }
        }
        model.addAttribute("notices", noticeVoList);
        model.addAttribute("page", page);

        // 设置已读
        List<Integer> ids = getLetterIds(noticeList);
        if (!ids.isEmpty()) {
            messageService.readMessage(ids);
        }

        return "bro/notice-detail";
    }

    private List<Integer> getLetterIds(List<Message> letterList) {
        List<Integer> ids = new ArrayList<>();

        if (letterList != null) {
            for (Message message : letterList) {
                if (hostHolder.getUser().getId().equals(message.getToUserId()) && message.getStatus().equals(0)) {
                    ids.add(message.getId());
                }
            }
        }

        return ids;
    }
}
