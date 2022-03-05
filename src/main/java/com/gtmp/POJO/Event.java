//package com.gtmp.entity;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class Event {
//    // kafka主题 1.评论 2.点赞 3.关注
//    private String topic;
//
//    // 事件触发的人的用户id （登录人的）
//    private Integer userId;
//
//    // 实体用户  （消息接收者id）id
//    private Integer toUserId;
//
//    // 实体类型 事件发生在哪个帖子之上   事件包括 点赞，回复，关注
//    // 1 - follow用户 ； 2 - 帖子的评论 对帖子的评论  3 帖子的评论的评论 对帖子评论的评论
//    private String objectType;
//
//    // 实体id  就是 帖子的id,评论id
//    // 1.对帖子的评论    2.对帖子评论的评论    就是 评论的id  3...
//    private Integer objectyId;
//
//    // 数据
//    private Map<String, Object> data = new HashMap<>();
//
//
//    public Event() {
//
//    }
//
//    public String getTopic() {
//        return topic;
//    }
//
//    public Event setTopic(String topic) {
//        this.topic = topic;
//        return this;
//
//    }
//
//    public Integer getUserId() {
//        return userId;
//    }
//
//    public Event setUserId(Integer userId) {
//        this.userId = userId;
//        return this;
//
//    }
//
//    public Integer getToUserId() {
//        return toUserId;
//    }
//
//    public Event setToUserId(Integer toUserId) {
//        this.toUserId = toUserId;
//        return this;
//
//    }
//
//    public String getObjectType() {
//        return objectType;
//    }
//
//    public Event setObjectType(String objectType) {
//        this.objectType = objectType;
//        return this;
//
//    }
//
//    public Integer getObjectyId() {
//        return objectyId;
//    }
//
//    public Event setObjectyId(Integer objectyId) {
//        this.objectyId = objectyId;
//        return this;
//
//    }
//
//    public Map<String, Object> getData() {
//        return data;
//    }
//
//    public Event setData(String key, Object value) {
//        this.data.put(key, value);
//        return this;
//    }
//}
