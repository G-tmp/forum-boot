package com.gtmp.service;


import com.gtmp.POJO.Notification;
import com.gtmp.POJO.Post;
import com.gtmp.POJO.Reply;
import com.gtmp.mapper.NotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.gtmp.POJO.Notification.Trigger.POST;

@Service
public class NotificationService {

    @Autowired
    NotificationMapper notificationMapper;

    @Autowired
    PostService postService;

    @Autowired
    ReplyService replyService;


    public Integer insertNotification(Notification notification){
        return notificationMapper.insertNotification(notification);
    }

    public List<Notification> listNotificationByUserId(Integer userId){
        List<Notification> list = notificationMapper.listNotificationByUserId(userId);
        for (Notification e:list){
            switch (Notification.Trigger.valueOf(e.getTriggerType())){
                case POST:
                    Post post = postService.selectPostById(e.getTriggerId());
                    e.setTrigger(post);
                    break;
                case REPLY:
                    Reply reply = replyService.selectReplyById(e.getTriggerId());
                    e.setTrigger(reply);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + e.getTriggerType());
            }
        }

        return list;
    }

    public int countUnreadNotificationByUserId(Integer userId){
        return notificationMapper.countUnreadNotificationByUserId(userId);
    }

    public void readNotification(Integer id){
        notificationMapper.readNotification(id);
    }

    public void readAllNotificationByUser(Integer userId){
        notificationMapper.readAllNotificationByUser(userId);
    }
}
