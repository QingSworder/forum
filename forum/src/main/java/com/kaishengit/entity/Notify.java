package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by wtj on 2016/12/27.
 */
public class Notify {
    private Integer id;
    private Integer userId;
    private String content;
    private Timestamp createTime;
    private Integer state;
    private Timestamp readTime;

    public static final Integer NOTIFY_STATE_READ=1;
    public static final Integer NOTIFY_STATE_UNREAD=0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getReadTime() {
        return readTime;
    }

    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }
}
