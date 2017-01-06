package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.util.Config;
import com.kaishengit.util.SqlSessionFactoryUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by wtj on 2016/12/28.
 */
public class AdminService {
    private AdminMapper adminMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(AdminMapper.class);

    private NodeMapper nodeMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(NodeMapper.class);
    private TopicMapper topicMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(TopicMapper.class);


    public Admin findAdminByUserName(String userName,String password,String ip) {
        Admin admin = adminMapper.findAdminByUserName(userName);
        if(admin!=null&&admin.getPassword().equals(DigestUtils.md5Hex(Config.get("user.password.salt")+password))){
            return admin;
        }else {
            throw new ServiceException("账号或密码错误");
        }

    }

    public void deleteTopicById(String id) {
        //根据topicId获取nodeId
        Topic topic = topicMapper.findTopicById(id);
        if(topic!=null){
            //根据topicId获取node
            Node node = nodeMapper.findNodeById(topic.getNodeId()) ;
            node.setTopicNum(node.getTopicNum()-1);
            nodeMapper.updateTopicNum(node);
            //删除帖子
            topicMapper.deleteTopicById(id);
        }else{
            throw new ServiceException("该主题不存在或已删除");
        }
    }

    public List<Node> findAllNodes() {
        return nodeMapper.findAllNodes();
    }

    public void deleteNodeById(Integer nodeId) {
        if(nodeId!=null){
            adminMapper.delNodeById(nodeId);
        }
    }
}
