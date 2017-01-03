package com.kaishengit.dao;

import com.kaishengit.entity.Topic;
import com.kaishengit.entity.TopicReplyCount;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import com.kaishengit.util.StringUtils;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by wtj on 2016/12/21.
 */
public class TopicDao {

    public Integer saveNewTopic(String title, String content, Integer nodeId, Integer userId) {
        String sql = "insert into t_topic(title,content,nodeId,userId) values(?,?,?,?)";
        return DbHelp.insert(sql,title,content,nodeId,userId);
    }

    public Topic findTopicById(String topicId) {
        String sql = "select * from t_topic where id = ?";
        return DbHelp.query(sql,new BeanHandler<>(Topic.class),topicId);
    }

    public void updateTopic(Topic topic){
        String sql = "update t_topic set title = ?,content = ?,clickNum = ?,favNum=?,thankNum=?,replyNum=?,lastReplyTime=?,nodeId=?,userId=? where id = ?";
        DbHelp.update(sql,topic.getTitle(),topic.getContent(),topic.getClickNum(),topic.getFavNum(),topic.getThankNum(),topic.getReplyNum(),topic.getLastReplyTime(),topic.getNodeId(),topic.getUserId(),topic.getId());
    }

    public int count() {
        String sql = "select count(*) from t_topic";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }
    public int count(String nodeId){
        String sql = "select count(*) from t_topic where nodeId = ? ";
        return DbHelp.query(sql,new ScalarHandler<Long>(),nodeId).intValue();
    }
    public List<Topic> findALl(HashMap<String,Object> map){
        String sql = "select tu.userName,tu.avatar,tt.* from t_topic tt,t_user tu where tu.id = tt.userId ";
        String nodeId = map.get("nodeId")==null?null:String.valueOf(map.get("nodeId"));
        String where = "";
        List<Object> array = new ArrayList<>();
        if(StringUtils.isNotEmpty(nodeId)){
            where += "and nodeId = ?";
            array.add(nodeId);
        }
        where += "order by tt.lastReplyTime desc limit ?,?";
        array.add(map.get("start"));
        array.add(map.get("pageSize"));
        sql += where;

        return DbHelp.query(sql, new AbstractListHandler<Topic>() {
            @Override
            protected Topic handleRow(ResultSet resultSet) throws SQLException {
                Topic topic = new BasicRowProcessor().toBean(resultSet,Topic.class);
                User user = new User();
                user.setId(resultSet.getInt("userId"));
                user.setUserName(resultSet.getString("userName"));
                user.setAvatar(Config.get("qiniu.domain")+resultSet.getString("avatar"));
                topic.setUser(user);

                return topic;
            }
        },array.toArray());


    }


    public void deleteTopicById(String id) {
        String sql = "delete from t_topic where id = ?";
        DbHelp.update(sql,id);
    }

    public int topicDayCount() {
        String sql = "select count(*) from (select count(*) from t_topic group by DATE_FORMAT(createTime,'%y-%m-%d')) AS topicCount";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<TopicReplyCount> getDayTopicAndReplyNumList(int start, int pageSize) {
        String sql = "SELECT COUNT(*) topicNum,DATE_FORMAT(createTime,'%y-%m-%d') 'time',\n" +
                "(SELECT COUNT(*) FROM t_comment WHERE DATE_FORMAT(createTime,'%y-%m-%d') \n" +
                "= DATE_FORMAT(t_topic.createTime,'%y-%m-%d')) 'replyNum'\n" +
                "FROM t_topic GROUP BY (DATE_FORMAT(createTime,'%y-%m-%d')) \n" +
                "ORDER BY (DATE_FORMAT(createTime,'%y-%m-%d')) DESC limit ?,?;";
        return DbHelp.query(sql,new BeanListHandler<TopicReplyCount>(TopicReplyCount.class),start,pageSize);
    }
}
