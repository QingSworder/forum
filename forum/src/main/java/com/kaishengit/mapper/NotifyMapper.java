package com.kaishengit.mapper;

import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by wtj on 2017/1/6.
 */
public interface NotifyMapper {
    List<Notify> findNotifyListByUser(User user);
    void save(Notify notify);
    void readNotify(Timestamp time, Integer id);
    Integer findUnReadCount(User user);
}
