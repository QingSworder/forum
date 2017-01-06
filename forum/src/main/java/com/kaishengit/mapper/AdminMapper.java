package com.kaishengit.mapper;

import com.kaishengit.entity.Admin;

/**
 * Created by wtj on 2017/1/6.
 */
public interface AdminMapper {
    Admin findAdminByUserName(String userName);
    void delNodeById(Integer nodeId);
}
