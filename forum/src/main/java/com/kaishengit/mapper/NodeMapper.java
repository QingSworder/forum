package com.kaishengit.mapper;

import com.kaishengit.entity.Node;

import java.util.List;

/**
 * Created by wtj on 2017/1/6.
 */
public interface NodeMapper {
    List<Node> findAllNodes();
    Node findNodeById(Integer nodeId);
    void updateTopicNum(Node node);
    Node findNodeByNodeName(String nodeName);
    void addNewNode(String nodeName);
    void updateNode(Node node);
}
