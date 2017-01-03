package com.kaishengit.dao;

import com.kaishengit.entity.Node;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.util.List;

/**
 * Created by wtj on 2016/12/22.
 */
public class NodeDao {

    public List<Node> findAllNodes() {
        String sql = "select * from t_node";
        return DbHelp.query(sql,new BeanListHandler<>(Node.class));
    }

    public Node findNodeById(Integer nodeId) {
        String sql = "select * from t_node where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(Node.class),nodeId);
    }

    public void updateTopicNum(Node node) {
        String sql = "update t_node set nodeName = ?,topicNum = ? where id = ?";
        DbHelp.update(sql,node.getNodeName(),node.getTopicNum(),node.getId());
    }

    public Node findNodeByNodeName(String nodeName) {
        String sql = "select * from t_node where nodeName = ?";
        return DbHelp.query(sql,new BeanHandler<Node>(Node.class),nodeName);
    }

    public static void addNewNode(String nodeName) {
        String sql = "insert into t_node(nodeName) values(?)";
        DbHelp.update(sql,nodeName);
    }

    public void updateNode(Node node) {
        String sql = "update t_node set nodeName = ? where id = ?";
        DbHelp.update(sql,node.getNodeName(),node.getId());
    }
}
