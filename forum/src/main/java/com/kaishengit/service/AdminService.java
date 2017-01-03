package com.kaishengit.service;

import com.kaishengit.dao.AdminDao;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dao.TopicDao;
import com.kaishengit.entity.Admin;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by wtj on 2016/12/28.
 */
public class AdminService {
    Logger logger = LoggerFactory.getLogger(Admin.class);
    private AdminDao adminDao = new AdminDao();
    TopicDao topicDao = new TopicDao();
    NodeDao nodeDao = new NodeDao();

    public Admin findAdminByUserName(String userName,String password,String ip) {
        Admin admin = adminDao.findAdminByUserName(userName);
        if(admin!=null&&admin.getPassword().equals(DigestUtils.md5Hex(Config.get("user.password.salt")+password))){
            return admin;
        }else {
            throw new ServiceException("账号或密码错误");
        }

    }

    public void deleteTopicById(String id) {
        //根据topicId获取nodeId
        Topic topic = topicDao.findTopicById(id);
        if(topic!=null){
            //根据topicId获取node
            Node node = nodeDao.findNodeById(topic.getNodeId()) ;
            node.setTopicNum(node.getTopicNum()-1);
            nodeDao.updateTopicNum(node);
            //删除帖子
            topicDao.deleteTopicById(id);
        }else{
            throw new ServiceException("该主题不存在或已删除");
        }
    }

    public List<Node> findAllNodes() {
        return nodeDao.findAllNodes();
    }

    public void deleteNodeById(Integer nodeId) {
        if(nodeId!=null){
            adminDao.delNodeById(nodeId);
        }
    }
}
