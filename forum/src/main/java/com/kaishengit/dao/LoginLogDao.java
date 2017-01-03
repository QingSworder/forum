package com.kaishengit.dao;

import com.kaishengit.entity.LoginLog;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by wtj on 2016/12/16.
 */
public class LoginLogDao {
    public static void save(LoginLog log){
        String sql = "insert into t_login_log(ip,t_user_id) values(?,?)";
        DbHelp.update(sql,log.getIp(),log.getT_user_id());
    }
}
