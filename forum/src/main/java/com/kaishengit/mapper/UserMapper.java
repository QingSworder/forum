package com.kaishengit.mapper;

import com.kaishengit.entity.User;
import com.kaishengit.util.Page;
import com.kaishengit.vo.UserVo;

import java.util.List;

/**
 * Created by wtj on 2017/1/6.
 */
public interface UserMapper {
    void save(User user);
    User findByUserName(String userName);
    User findByEmail(String email);
    void update(User user);
    User findById(Integer id);
    List findAllFavByUserId(Integer id);
    void favTopic(String topicId, Integer UserId);
    void unFavTopic(String topicId, Integer userId);
    Integer count();
    List<User> findAllUsers(Page<UserVo> page);
    UserVo findUserVo(Integer id);
}
