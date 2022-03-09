package com.gtmp.POJO;



import java.util.Date;

public class Post {

    private Integer id;
    private String title;
    private String content;
    private Integer status;     // 0-delete; 1-normal display. default 1
    private Date createTime;
    private Date lastModifiedTime;
    private Integer replyCount;
    private Double score;
    private Integer userId;
    private User user;
    private Integer boardId;
    private Board board;



    public Post() {
    }

    public Integer getId() {
        return id;
    }

    public Post setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getBoardId() {
        return boardId;
    }

    public Post setBoardId(Integer boardId) {
        this.boardId = boardId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Post setUserId(Integer userId) {
        this.userId = userId;
        return this;

    }

    public String getTitle() {
        return title;
    }

    public Post setTitle(String title) {
        this.title = title;
        return this;

    }

    public String getContent() {
        return content;
    }

    public Post setContent(String content) {
        this.content = content;
        return this;

    }


    public Integer getStatus() {
        return status;
    }

    public Post setStatus(Integer status) {
        this.status = status;
        return this;

    }

    public Date getCreateTime() {
        return createTime;
    }

    public Post setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;

    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public Post setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
        return this;

    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public Post setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
        return this;

    }

    public Double getScore() {
        return score;
    }

    public Post setScore(Double score) {
        this.score = score;
        return this;

    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", boardId=" + boardId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", replyCount=" + replyCount +
                ", score=" + score +
                '}';
    }


    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
