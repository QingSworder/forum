package com.kaishengit.service;

import com.google.common.collect.Maps;
import com.kaishengit.dao.*;
import com.kaishengit.entity.*;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.util.Config;
import com.kaishengit.util.Page;
import com.kaishengit.util.SqlSessionFactoryUtil;
import com.kaishengit.util.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wtj on 2016/12/21.
 */
public class TopicService {


    private UserMapper userMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(UserMapper.class);
    private NodeMapper nodeMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(NodeMapper.class);
    private TopicMapper topicMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(TopicMapper.class);
    private CommentMapper commentMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(CommentMapper.class);
    private NotifyMapper notifyMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(NotifyMapper.class);

    /**
     * 查找所有节点
     * @return
     */
    public List<Node> findAllNodes() {
        return nodeMapper.findAllNodes();
    }

    public Topic addNewTopic(String title, String content, Integer nodeId, Integer userId) {
        Topic topic = new Topic();
        topic.setId(topicMapper.saveNewTopic(title,content,nodeId,userId));
        topic.setTitle(title);
        topic.setContent(content);
        topic.setNodeId(nodeId);
        topic.setUserId(userId);
        topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
        //更新node表的topicnum
        Node node = nodeMapper.findNodeById(nodeId);
        if(node!=null){
            node.setTopicNum(node.getTopicNum()+1);
            nodeMapper.updateTopicNum(node);
        } else {
            throw new ServiceException("节点不存在");
        }
        return topic;

    }

    public Topic findTopicById(String topicId) {

        if(StringUtils.isNumeric(topicId)){
            Topic topic = topicMapper.findTopicById(topicId);
            if(topic!=null){
                //通过topic对象的userId，nodeId获取user和node对象 并封存到topic中
                User user =userMapper.findById(topic.getUserId());
                Node node = nodeMapper.findNodeById(topic.getNodeId());
                user.setAvatar(Config.get("qiniu.domain")+user.getAvatar());
                topic.setUser(user);
                topic.setNode(node);


                //更新topic表中的clickNum字段
                topic.setClickNum(topic.getClickNum()+1);
                topicMapper.updateTopic(topic);
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
        Topic topic = topicMapper.findTopicById(topicId);
        topic.setReplyNum(topic.getReplyNum()+1);
        //更新帖子的最后回复时间
        topic.setLastReplyTime(new Timestamp(new DateTime().getMillis()));
        topicMapper.updateTopic(topic);
        commentMapper.addTopicReply(topicId,content,user.getId());
        //更新消息表 如果当前帖子的userId和当前登录的userId相同，则插入该回复到消息表
        //如果是自己回复，则不插入
        if(topic.getUserId()!=user.getId()){
            Notify notify = new Notify();
            notify.setUserId(topic.getUserId());
            //notify.setContent("您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">["+ topic.getTitle()+"] </a> 有了新的回复,请查看.");
            notify.setContent("您的主题 <a href=\"/topicDetail?topicid="+topic.getId()+"\">["+topic.getTitle()+"] </a> 有了新的回复，请查看.");
            //notify.setCreateTime(new Timestamp(new DateTime().getMillis()));
            notify.setState(Notify.NOTIFY_STATE_UNREAD);
            notifyMapper.save(notify);
        }
    }

    public List<Comment> findAllCommentsById(String topicId) {
        return commentMapper.findAllCommentsById(topicId);
    }


    public void updateTopicById(String title, String content, String nodeId, String topicId) {
        Topic topic = topicMapper.findTopicById(topicId);
        if(topic.isEdit()) {
            topic.setTitle(title);
            topic.setContent(content);
            topic.setNodeId(Integer.valueOf(nodeId));
            topicMapper.updateTopic(topic);
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
        return userMapper.findAllFavByUserId(id);
    }

    /**
     * 收藏帖子或者取消收藏，并更新该帖子的favNum
     * @param topicId
     * @param action
     */
    public void favTopicOrNot(String topicId,User user, String action) {
        Topic topic = topicMapper.findTopicById(topicId);
        if("fav".equals(action)){
            userMapper.favTopic(topicId,user.getId());
            topic.setFavNum(topic.getFavNum()+1);
        }else{
            userMapper.unFavTopic(topicId,user.getId());
            topic.setFavNum(topic.getFavNum()-1);
        }
        topicMapper.updateTopic(topic);
    }

    public Page<Topic> findAllTopics(String nodeId,Integer pageNo) {
        HashMap<String,Object> map = Maps.newHashMap();
        int count = 0;
        if(StringUtils.isEmpty(nodeId)){
            count = topicMapper.count();
        }else {
            count = topicMapper.countByNodeId(nodeId);
        }

        Page<Topic> topicPage = new Page<>(count,pageNo);
        map.put("nodeId",nodeId);
        map.put("start",topicPage.getStart());
        map.put("pageSize",topicPage.getPageSize());

        List<Topic> topicList = topicMapper.findAll(map);
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
            notifyMapper.readNotify(time,Integer.valueOf(idS[i]));
        }
    }

    public Page<TopicReplyCount> getDayTopicAndReplyNumList(Integer pageNo) {
        int count = topicMapper.topicDayCount();
        Page<TopicReplyCount> page = new Page<>(count,pageNo);
        List<TopicReplyCount> countList = topicMapper.getDayTopicAndReplyNumList(page.getStart(),page.getPageSize());
        page.setItems(countList);
        return page;
    }
}
