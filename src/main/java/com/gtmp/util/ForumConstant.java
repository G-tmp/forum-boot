package com.gtmp.util;

public interface ForumConstant {
    //注册邮件激活成功
    int ACTIVATION_SUCCESS = 0;

    //注册邮件重复激活
    int ACTIVATION_REPEAT = 1;

    //注册邮件激活失败
    int ACTIVATION_FAILURE = 2;

    //默认状态下登录凭证失效时间: 6 小时
    int DEFAULT_EXPIRED_SECONDS = 60 * 60 * 6;

    //记住状态下登录凭证失效时间: 1 天
    int REMEMBER_EXPIRED_SECONDS = 60 * 60 * 24 * 1;


//    // 关注实体类型：用户 关注用户
//    int ENTITY_TYPE_USER = 1;
//
//    // 评论实体类型：帖子
//    int ENTITY_TYPE_POST = 2;
//
//    //  评论实体类型：评论 对评论进行评论
//    int ENTITY_TYPE_REPLY = 3;

    // Kafka 评论 点赞 关注 发帖 Topic
    String QUEUE_REPLY = "reply";
    String QUEUE_LIKE = "like";
    String QUEUE_FOLLOW = "follow";
    String QUEUE_PUBLISH = "publish";

    // 系统用户 ID
    int SYSTEM_USER_ID = 0;


}
