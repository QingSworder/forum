package com.kaishengit.dao;

import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wtj on 2016/12/27.
 */
public class NotifyDao {
    public List<Notify> findNotifyListByUser(User user) {
        String sql = "select * from t_notify where userId = ? order by readTime,createTime";
        return DbHelp.query(sql,new BeanListHandler<>(Notify.class),user.getId());
    }

    public void save(Notify notify) {
        String sql = "insert into t_notify(userId,content,state) values(?,?,?)";
        DbHelp.update(sql,notify.getUserId(),notify.getContent(),notify.getState());
    }

    public void readNotify(Timestamp time,Integer id) {
        String sql = "update t_notify set state = 1,readTime = ? where id = ? ";
        DbHelp.update(sql,time,id);
    }

    public Integer findUnReadCount(User user) {
        String sql = "select count(*) from t_notify where state = 0 and userId = ?";
        return  DbHelp.query(sql,new ScalarHandler<Long>(),user.getId()).intValue();
    }
}
