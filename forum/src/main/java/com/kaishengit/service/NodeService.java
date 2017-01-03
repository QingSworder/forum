package com.kaishengit.service;

import com.kaishengit.dao.NodeDao;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;

/**
 * Created by wtj on 2016/12/29.
 */
public class NodeService {
    private NodeDao nodeDao = new NodeDao();

    public Node findNodeByNodeName(String nodeName) {

        return nodeDao.findNodeByNodeName(nodeName);
    }

    public void addNewNode(String nodeName) {
        NodeDao.addNewNode(nodeName);
    }

    public Node findNodeByNodeId(Integer nodeId) {
        return nodeDao.findNodeById(nodeId);
    }

    public void updateNodeById(String nodeName, String nodeId) {
        Node node = nodeDao.findNodeById(Integer.valueOf(nodeId));
        if(node!=null){
            node.setNodeName(nodeName);
            nodeDao.updateNode(node);
        }else {
            throw new ServiceException("该nodeId不存在");
        }
    }
}
