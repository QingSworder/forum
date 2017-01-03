package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by wtj on 2016/12/24.
 */
public class Fav {
    private Integer userId;
    private Integer topicId;
    private Timestamp createTime;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTopicId() {
        return topicId;
    }

    public void setTopicId(Integer topicId) {
        this.topicId = topicId;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
