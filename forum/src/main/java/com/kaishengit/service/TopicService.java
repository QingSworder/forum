package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.util.Config;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wtj on 2016/12/21.
 */
public class TopicService {
    private NodeDao nodeDao = new NodeDao();
    private TopicDao topicDao = new TopicDao();
    private CommentDao commentDao = new CommentDao();
    private UserDao userDao = new UserDao();
    private NotifyDao notifyDao = new NotifyDao();

    /**
     * 查找所有节点
     * @return
     */
    public List<Node> findAllNodes() {
        return nodeDao.findAllNodes();
    }

    public Topic addNewTopic(String title, String content, Integer nodeId, Integer userId) {
        Topic topic = new Topic();
        topic.setId(topicDao.saveNewTopic(title,content,nodeId,userId));
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeId(nodeId);
        topic.setUserId(userId);
        topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
        //更新node表的topicnum
        Node node = nodeDao.findNodeById(nodeId);
        if(node!=null){
            node.setTopicNum(node.getTopicNum()+1);
            nodeDao.updateTopicNum(node);
        } else {
            throw new ServiceException("节点不存在");
        }
        return topic;

    }

    public Topic findTopicById(String topicId) {

        if(StringUtils.isNumeric(topicId)){
            Topic topic = topicDao.findTopicById(topicId);
            if(topic!=null){
                //通过topic对象的userId，nodeId获取user和node对象 并封存到topic中
                User user = userDao.findById(topic.getUserId());
                Node node = nodeDao.findNodeById(topic.getNodeId());
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);


                //更新topic表中的clickNum字段
                topic.setClickNum(topic.getClickNum()+1);
                topicDao.updateTopic(topic);
                return topic;
            }else{
                throw new ServiceException("该帖不存在或已删除");
            }

        }else {
            throw  new ServiceException("参数错误");
        }

    }

    public void addTopicReply(String topicId, String content, User user) {
        //更新topic表的评论数
        Topic topic = topicDao.findTopicById(topicId);
        topic.setReplyNum(topic.getReplyNum()+1);
        //更新帖子的最后回复时间
        topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
        topicDao.updateTopic(topic);
        commentDao.addTopicReply(topicId,content,user.getId());
        //更新消息表 如果当前帖子的userId和当前登录的userId相同，则插入该回复到消息表
        //如果是自己回复，则不插入
        if(topic.getUserId()!=user.getId()){
            Notify notify = new Notify();
            notify.setUserId(topic.getUserId());
            //notify.setContent("您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">["+ topic.getTitle()+"] </a> 有了新的回复,请查看.");
            notify.setContent("您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">["+topic.getTitle()+"] </a> 有了新的回复，请查看.");
            //notify.setCreateTime(new Timestamp(new DateTime().getMillis()));
            notify.setState(Notify.NOTIFY_STATE_UNREAD);
            notifyDao.save(notify);
        }
    }

    public List<Comment> findAllCommentsById(String topicId) {
        return commentDao.findAllCommentsById(topicId);
    }


    public void updateTopicById(String title, String content, String nodeId, String topicId) {
        Topic topic = topicDao.findTopicById(topicId);
        if(topic.isEdit()) {
            topic.setTitle(title);
            topic.setContent(content);
            topic.setNodeId(Integer.valueOf(nodeId));
            topicDao.updateTopic(topic);
        }else{
            throw new ServiceException("帖子已不能编辑");
        }
    }

    /**
     * 查找某用户收藏的所有的帖子
     * @param id
     * @return
     */
    public List<Fav> findAllFavByUserId(Integer id) {
        return userDao.findAllFavByUserId(id);
    }

    /**
     * 收藏帖子或者取消收藏，并更新该帖子的favNum
     * @param topicId
     * @param action
     */
    public void favTopicOrNot(String topicId,User user, String action) {
        Topic topic = topicDao.findTopicById(topicId);
        if("fav".equals(action)){
            userDao.favTopic(topicId,user.getId());
            topic.setFavNum(topic.getFavNum()+1);
        }else{
            userDao.unFavTopic(topicId,user.getId());
            topic.setFavNum(topic.getFavNum()-1);
        }
        topicDao.updateTopic(topic);
    }

    public Page<Topic> findAllTopics(String nodeId,Integer pageNo) {
        HashMap<String,Object> map = Maps.newHashMap();
        int count = 0;
        if(StringUtils.isEmpty(nodeId)){
            count = topicDao.count();
        }else {
            count = topicDao.count(nodeId);
        }

        Page<Topic> topicPage = new Page<>(count,pageNo);
        map.put("nodeId",nodeId);
        map.put("start",topicPage.getStart());
        map.put("pageSize",topicPage.getPageSize());

        List<Topic> topicList = topicDao.findALl(map);
        topicPage.setItems(topicList);
        return topicPage;

    }

    /**
     * 改变已读消息存储状态
     * @param idS
     */
    public void readNotify(String[] idS) {
        Timestamp time = new Timestamp(new DateTime().getMillis());
        for(int i=0;i< idS.length;i++){
            notifyDao.readNotify(time,Integer.valueOf(idS[i]));
        }
    }

    public Page<TopicReplyCount> getDayTopicAndReplyNumList(Integer pageNo) {
        int count = topicDao.topicDayCount();
        Page<TopicReplyCount> page = new Page<>(count,pageNo);
        List<TopicReplyCount> countList = topicDao.getDayTopicAndReplyNumList(page.getStart(),page.getPageSize());
        page.setItems(countList);
        return page;
    }
}
