package com.kaishengit.entity;

import java.sql.Timestamp;

/**
 * Created by wtj on 2016/12/15.
 */
public class User {
    /**
     * 新用户默认头像key
     */
    public static final String DEFAULT_AVATAR_NAME = "default_avatar.png";
    /**
     * 用户状态:未激活
     */
    public static final Integer USERSTATE_UNACTIVE = 0;
    /**
     * 用户状态：已激活
     */
    public static final Integer USERSTATE_ACTIVE = 1;
    /**
     * 用户状态：已禁用
     */
    public static final Integer USERSTATE_DISABLED = 2;
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private Integer state;
    private Timestamp createTime;
    private String avatar;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
