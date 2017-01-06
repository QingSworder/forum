package com.kaishengit.mapper;

import com.kaishengit.entity.Comment;

import java.util.List;

/**
 * Created by wtj on 2017/1/6.
 */
public interface CommentMapper {
    void addTopicReply(String topicId, String content, Integer userId);
    List<Comment> findAllCommentsById(String topicId);
}
