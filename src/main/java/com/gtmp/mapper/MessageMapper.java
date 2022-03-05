package com.gtmp.mapper;

import com.gtmp.POJO.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MessageMapper {

    /**
     * 查询当前用户的会话列表，针对每个会话只返回一条最新的私信。
     */
    List<Message> selectConversations(int userId, int offset, int limit);

    /**
     * 查询当前用户的会话数量
     */
    Integer selectConversationCount(int userId);

    /**
     * 查询某个会话所包含的私信列表
     */
    List<Message> selectLetters(String conversationType, int offset, int limit);

    /**
     * 查询某个会话所包含的私信数量
     */
    Integer selectLetterCount(String conversationType);

    /**
     * 查询未读私信数量
     */
    Integer selectLetterUnreadCount(int userId, String conversationType);

    /**
     * 新增消息
     */
    Integer insertMessage(Message message);

    /**
     * 修改消息的状态
     */
    Integer updateStatus(List<Integer> ids, int status);

    /**
     * 查询某个主题下最新的通知
     */
    Message selectLatestNotice(int userId, String topic);

    /**
     * 查询某个主题所包含的通知的数量
     */
    Integer selectNoticeCount(int userId, String topic);

    /**
     * 查询未读的通知的数量
     */
    int selectNoticeUnreadCount(int userId, String topic);

    /**
     * 查询某个主题所包含的通知列表
     */
    List<Message> selectNotices(int userId, String topic, int offset, int limit);
}
