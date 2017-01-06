package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.NodeMapper;
import com.kaishengit.util.SqlSessionFactoryUtil;

/**
 * Created by wtj on 2016/12/29.
 */
public class NodeService {
    private NodeDao nodeDao = new NodeDao();
    private NodeMapper nodeMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(NodeMapper.class);

    public Node findNodeByNodeName(String nodeName) {

        return nodeMapper.findNodeByNodeName(nodeName);
    }

    public void addNewNode(String nodeName) {
        nodeMapper.addNewNode(nodeName);
    }

    public Node findNodeByNodeId(Integer nodeId) {
        return nodeMapper.findNodeById(nodeId);
    }

    public void updateNodeById(String nodeName, String nodeId) {
        Node node = nodeMapper.findNodeById(Integer.valueOf(nodeId));
        if(node!=null){
            node.setNodeName(nodeName);
            nodeMapper.updateNode(node);
        }else {
            throw new ServiceException("该nodeId不存在");
        }
    }
}
