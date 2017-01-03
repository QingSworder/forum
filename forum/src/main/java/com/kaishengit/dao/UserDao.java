package com.kaishengit.dao;

import com.kaishengit.entity.Fav;
import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import com.kaishengit.util.Page;
import com.kaishengit.vo.UserVo;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.util.List;

/**
 * Created by wtj on 2016/12/15.
 */
public class UserDao {
    public void save(User user) {
        String sql = "insert into t_user(userName,password,email,phone,avatar,state) values(?,?,?,?,?,?)";
        DbHelp.update(sql,user.getUserName(), user.getPassword(),user.getEmail(),user.getPhone(),user.getAvatar(),user.getState());
    }

    public User findByUserName(String userName) {
        String sql = "select * from t_user where userName = ?";
        return DbHelp.query(sql,new BeanHandler<>(User.class),userName);
    }
    public User findByEmail(String email){
        String sql = "select * from t_user where email = ?";
        return  DbHelp.query(sql,new BeanHandler<>(User.class),email);
    }

    public void update(User user) {
        String sql = "update t_user set password = ?,email=?,phone=?,state=?,avatar=? where id = ?";
        DbHelp.update(sql,user.getPassword(),user.getEmail(),user.getPhone(),user.getState(),user.getAvatar(),user.getId());
    }

    public User findById(Integer id) {
        String sql = "select * from t_user where id = ?";
        return DbHelp.query(sql,new BeanHandler<User>(User.class),id);
    }

    public List findAllFavByUserId(Integer id) {
        String sql = "select * from t_fav where userId = ?";
        return DbHelp.query(sql,new BeanListHandler<>(Fav.class),id);
    }

    public void favTopic(String topicId, Integer UserId) {
        String sql = "insert into t_fav(topicId,userId) values(?,?)";
        DbHelp.update(sql,topicId,UserId);
    }

    public void unFavTopic(String topicId, Integer userId) {
        String sql = "delete from t_fav where userId = ? and topicId = ?";
        DbHelp.update(sql,userId,topicId);
    }


    public Integer count() {
        //获取所有状态不为0用户的数量
        String sql = "select count(*) from t_user where state !=0 order by id";
        return DbHelp.query(sql,new ScalarHandler<Long>()).intValue();
    }

    public List<User> findAllUsers(Page<UserVo> page) {
        //分页查询所有状态不为0的用户
        String sql = "select * from t_user where state !=0 order by createTime limit ?,?";
        return DbHelp.query(sql,new BeanListHandler<User>(User.class),page.getStart(),page.getPageSize());
    }

    public UserVo findUserVo(Integer id) {
        String sql = "select max(tl.login_time) lastLoginTime,tl.ip loginIp,tu.id userId,tu.userName userName,tu.createTime,tu.state userState from t_login_log tl,t_user tu where tu.id = ?  ";
        return DbHelp.query(sql,new BeanHandler<>(UserVo.class),id);
    }
}
