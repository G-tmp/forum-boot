package com.gtmp.POJO;


import java.util.Date;


/**
 * Composite Primary Key
 *
 * @userId
 * @objectType
 * @objectId
 */
public class Like {
    private Integer userId;
    private String objectType;
    private Integer objectId;
    private Integer status;
    private Date createTime;

    public Like() {
    }

    public Integer getUserId() {
        return userId;
    }

    public Like setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getObjectType() {
        return objectType;
    }

    public Like setObjectType(String objectType) {
        this.objectType = objectType;
        return this;

    }

    public Integer getObjectId() {
        return objectId;
    }

    public Like setObjectId(Integer objectId) {
        this.objectId = objectId;
        return this;

    }

    public Integer getStatus() {
        return status;
    }

    public Like setStatus(Integer status) {
        this.status = status;
        return this;

    }

    public Date getCreateTime() {
        return createTime;
    }

    public Like setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;

    }

    @Override
    public String toString() {
        return "Like{" +
                "userId=" + userId +
                ", objectType='" + objectType + '\'' +
                ", objectId=" + objectId +
                ", status=" + status +
                ", createTime=" + createTime +
                '}';
    }


}
