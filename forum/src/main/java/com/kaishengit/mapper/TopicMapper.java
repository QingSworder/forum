package com.kaishengit.mapper;

import com.kaishengit.entity.Topic;
import com.kaishengit.entity.TopicReplyCount;

import java.util.HashMap;
import java.util.List;

/**
 * Created by wtj on 2017/1/6.
 */
public interface TopicMapper {
    Integer saveNewTopic(String title, String content, Integer nodeId, Integer userId);
    Topic findTopicById(String topicId);
    void updateTopic(Topic topic);
    int count();
    int countByNodeId(String nodeId);
    List<Topic> findAll(HashMap<String,Object> map);
    void deleteTopicById(String id);
    int topicDayCount();
    List<TopicReplyCount> getDayTopicAndReplyNumList(int start, int pageSize);
}
