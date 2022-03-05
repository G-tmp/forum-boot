package com.gtmp.POJO;

import java.util.Date;

/**
 * 消息通知
 */
public class Notify  {
    private Integer id;
    private Integer fromUserId;
    private Integer toUserId;
    private Integer status;
    private String action;
    private Integer objectId;
    private String objectType;
    private Date createTime;
    private String content;

    public Notify() {

    }


    public Integer getId() {
        return id;
    }

    public Notify setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public Notify setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
        return this;

    }

    public Integer getToUserId() {
        return toUserId;
    }

    public Notify setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
        return this;

    }

    public Integer getStatus() {
        return status;
    }

    public Notify setStatus(Integer status) {
        this.status = status;
        return this;

    }

    public Integer getObjectId() {
        return objectId;
    }

    public Notify setObjectId(Integer objectId) {
        this.objectId = objectId;
        return this;

    }

    public String getObjectType() {
        return objectType;
    }

    public Notify setObjectType(String objectType) {
        this.objectType = objectType;
        return this;

    }

    public Date getCreateTime() {
        return createTime;
    }

    public Notify setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;

    }

    public String getContent() {
        return content;
    }

    public Notify setContent(String content) {
        this.content = content;
        return this;

    }


    public String getAction() {
        return action;
    }

    public Notify setAction(String action) {
        this.action = action;
        return this;

    }

    @Override
    public String toString() {
        return "Notify{" +
                "id=" + id +
                ", fromUserId=" + fromUserId +
                ", toUserId=" + toUserId +
                ", status=" + status +
                ", action='" + action + '\'' +
                ", objectId=" + objectId +
                ", objectType='" + objectType + '\'' +
                ", createTime=" + createTime +
                ", content='" + content + '\'' +
                '}';
    }
}
