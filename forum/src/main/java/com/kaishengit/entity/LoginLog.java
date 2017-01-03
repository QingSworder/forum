package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by wtj on 2016/12/15.
 */
public class LoginLog {
    private Integer id;
    private Timestamp loginTime;
    private String ip;
    private Integer t_user_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getT_user_id() {
        return t_user_id;
    }

    public void setT_user_id(Integer t_user_id) {
        this.t_user_id = t_user_id;
    }
}
