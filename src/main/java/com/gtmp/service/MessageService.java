package com.gtmp.service;

import com.gtmp.POJO.Message;
import com.gtmp.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MessageService {
    @Resource
    MessageMapper messageMapper;


    public List<Message> selectConversations(int userId, int offset, int limit) {
        return messageMapper.selectConversations(userId, offset, limit);
    }

    public Integer selectConversationCount(int userId) {
        return messageMapper.selectConversationCount(userId);
    }

    public List<Message> selectLetters(String conversationId, int offset, int limit) {
        return messageMapper.selectLetters(conversationId, offset, limit);
    }

    public Integer selectLetterCount(String conversationType) {
        return messageMapper.selectLetterCount(conversationType);
    }

    public Integer selectLetterUnreadCount(int userId, String conversationType) {
        return messageMapper.selectLetterUnreadCount(userId, conversationType);
    }

    public Integer insertMessage(Message message) {
        message.setContent(HtmlUtils.htmlEscape(message.getContent()));
//        message.setContent(sensitiveFilter.filter(message.getContent()));
        return messageMapper.insertMessage(message);
    }

    public Integer readMessage(List<Integer> ids) {
        return messageMapper.updateStatus(ids, 1);
    }

    public Message selectLatestNotice(int userId, String topic) {
        return messageMapper.selectLatestNotice(userId, topic);
    }

    public Integer selectNoticeCount(int userId, String topic) {
        return messageMapper.selectNoticeCount(userId, topic);
    }

    public Integer selectNoticeUnreadCount(int userId, String topic) {
        return messageMapper.selectNoticeUnreadCount(userId, topic);
    }

    public List<Message> selectNotices(int userId, String topic, int offset, int limit) {
        return messageMapper.selectNotices(userId, topic, offset, limit);
    }
}
