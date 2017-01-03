package com.kaishengit.dao;

import com.kaishengit.entity.Comment;
import com.kaishengit.entity.User;
import com.kaishengit.util.Config;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.handlers.AbstractListHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by wtj on 2016/12/22.
 */
public class CommentDao {
    public void addTopicReply(String topicId, String content, Integer userId) {
        String sql = "insert into t_comment(topicId,content,userId) values(?,?,?)";
        DbHelp.update(sql,topicId,content,userId);
    }
    public List<Comment> findAllCommentsById(String topicId) {
        String sql = "select tu.id,tu.avatar,tu.userName,tc.* FROM t_user tu,t_comment tc WHERE tu.id = tc.userId AND tc.topicId = ?";
        return DbHelp.query(sql, new AbstractListHandler<Comment>() {
            @Override
            protected Comment handleRow(ResultSet resultSet) throws SQLException {
                Comment comment = new BasicRowProcessor().toBean(resultSet,Comment.class);
                User user = new User();
                user.setAvatar(Config.get("qiniu.domain")+resultSet.getString("avatar"));
                user.setUserName(resultSet.getString("userName"));
                user.setId(resultSet.getInt("id"));
                comment.setUser(user);
                return comment;
            }
        },topicId);
    }
}
