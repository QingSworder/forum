package com.kaishengit.dao;

import com.kaishengit.entity.Admin;
import com.kaishengit.util.DbHelp;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * Created by wtj on 2016/12/28.
 */
public class AdminDao {
    public Admin findAdminByUserName(String userName) {
        String sql = "select * from t_admin where userName = ?";
        return DbHelp.query(sql,new BeanHandler<>(Admin.class),userName);
    }

    public void delNodeById(Integer nodeId) {
        String sql = "delete from t_node where id = ?";
        DbHelp.update(sql,nodeId);
    }
}
