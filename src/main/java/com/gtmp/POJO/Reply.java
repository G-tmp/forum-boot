package com.gtmp.POJO;

import java.util.Date;

public class Reply {
    private Integer id;
    private String content;
    private Integer userId;
    private Integer postId;
    private Date createTime;
    private Integer floor;
    private Integer status;

    public Reply() {

    }

    public Integer getId() {
        return id;
    }

    public Reply setId(Integer id) {
        this.id = id;
        return this;

    }

    public String getContent() {
        return content;
    }

    public Reply setContent(String content) {
        this.content = content;
        return this;

    }

    public Integer getUserId() {
        return userId;
    }

    public Reply setUserId(Integer userId) {
        this.userId = userId;
        return this;

    }

    public Integer getPostId() {
        return postId;
    }

    public Reply setPostId(Integer postId) {
        this.postId = postId;
        return this;

    }


    public Integer getFloor() {
        return floor;
    }

    public Reply setFloor(Integer floor) {
        this.floor = floor;
        return this;

    }

    public Integer getStatus() {
        return status;
    }

    public Reply setStatus(Integer status) {
        this.status = status;
        return this;

    }

    @Override
    public String toString() {
        return "Reply{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", postId=" + postId +
                ", floor=" + floor +
                ", status=" + status +
                '}';
    }


    public Date getCreateTime() {
        return createTime;
    }

    public Reply setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }


}
