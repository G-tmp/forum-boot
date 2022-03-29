package com.gtmp.POJO;

import java.util.Date;

public class Notification {
    private Integer id;
    private Integer fromUserId;
    private User fromUser;
    private Integer toUserId;
    private User toUser;
    private String event;
    private String triggerType;
    private Integer triggerId;
    private Object trigger;
    private Integer read;
    private Date createTime;

    public enum Event{
        MENTION,
        REPLY,
        TO,
        LIKE;
    }

    public enum Trigger{
        USER,
        POST,
        REPLY;
    }

    public Notification(){
    }

    public Object getTrigger() {
        return trigger;
    }

    public Notification setTrigger(Object trigger) {
        this.trigger = trigger;
        return this;
    }

    public String getTriggerType() {
        return triggerType;
    }

    public Notification setTriggerType(String triggerType) {
        this.triggerType = triggerType;
        return this;
    }

    public Integer getTriggerId() {
        return triggerId;
    }

    public Notification setTriggerId(Integer triggerId) {
        this.triggerId = triggerId;
        return this;
    }


    public Integer getId() {
        return id;
    }

    public Notification setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public Notification setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
        return this;
    }

    public User getFromUser() {
        return fromUser;
    }

    public Notification setFromUser(User fromUser) {
        this.fromUser = fromUser;return this;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public Notification setToUserId(Integer toUserId) {
        this.toUserId = toUserId;return this;
    }

    public User getToUser() {
        return toUser;
    }

    public Notification setToUser(User toUser) {
        this.toUser = toUser;return this;
    }

    public Integer getRead() {
        return read;
    }

    public Notification setRead(Integer read) {
        this.read = read;return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Notification setCreateTime(Date createTime) {
        this.createTime = createTime;return this;
    }

    public String getEvent() {
        return event;
    }

    public Notification setEvent(String event) {
        this.event = event;return this;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", fromUser=" + fromUser +
                ", toUserId=" + toUserId +
                ", toUser=" + toUser +
                ", event='" + event + '\'' +
                ", triggerType='" + triggerType + '\'' +
                ", triggerId=" + triggerId +
                ", trigger=" + trigger +
                ", read=" + read +
                ", createTime=" + createTime +
                '}';
    }
}
