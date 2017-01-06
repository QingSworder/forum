package com.kaishengit.service;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.kaishengit.dao.LoginLogDao;
import com.kaishengit.dao.NotifyDao;
import com.kaishengit.entity.LoginLog;
import com.kaishengit.entity.Notify;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.mapper.*;
import com.kaishengit.util.*;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.vo.UserVo;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * Created by wtj on 2016/12/15.
 */
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private UserMapper userMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(UserMapper.class);

    private NotifyMapper notifyMapper = SqlSessionFactoryUtil.getSqlSession().getMapper(NotifyMapper.class);


    //发送激活邮件的TOKEN缓存
    private static Cache<String,String> cache = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.HOURS)
            .build();
    //限制操作频率的缓存
    private static Cache<String,String> activeCache = CacheBuilder.newBuilder()
            .expireAfterWrite(60,TimeUnit.SECONDS)
            .build();
    //发送找回密码的TOKEN缓存
    private static Cache<String,String> passwordCache = CacheBuilder.newBuilder()
            .expireAfterWrite(30,TimeUnit.MINUTES)
            .build();
    /**
     * 校验用户名是否被占用
     */
    public boolean validateUserName(String userName){
        //保留名字
        String name = Config.get("remain.username");
        List<String> nameList = Arrays.asList(name.split(","));
        if(nameList.contains(userName)){
            return false;
        }
        return userMapper.findByUserName(userName) == null;
    }

    /**
     * 注册新用户
     * @param userName
     * @param password
     * @param email
     * @param phone
     */
    public void save(String userName, String password, String email, String phone) {
        User user = new User();
        user.setUserName(userName);
        user.setAvatar(User.DEFAULT_AVATAR_NAME);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPassword(DigestUtils.md5Hex(Config.get("user.password.salt") + password));
        user.setState(User.USERSTATE_UNACTIVE);

        userMapper.save(user);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //给用户发送激活邮件
                String uuid = UUID.randomUUID().toString();
                String url = "http://localhost/user/active?_="+uuid;
                //放入缓存等待6个小时
                cache.put(uuid,userName);

                String html ="<h3>Dear "+userName+":</h3>请点击<a href='"+url+"'>该链接</a>去激活你的账号. <br> izangg";

                EmailUtil.sendHtmlEmail(email,"用户激活邮件",html);
            }
        });
        thread.start();
    }
    public User findByEmail(String email) {
        return userMapper.findByEmail(email);
    }

    /**
     * 用户登录
     * @param userName
     * @param password
     * @param ip
     * @return
     */
    public User login(String userName, String password, String ip) {
        User user = userMapper.findByUserName(userName);

        logger.info(user.getUserName());
        logger.info(user.getPassword());
        if(user != null&&(DigestUtils.md5Hex(Config.get("user.password.salt")+password).equals(user.getPassword()))){
            if(user.getState().equals(User.USERSTATE_ACTIVE)){
                //记录登录日志
                LoginLog log = new LoginLog();
                log.setIp(ip);
                log.setT_user_id(user.getId());

                LoginLogDao.save(log);
                logger.info("{}登录了系统,ip{}",user.getUserName(),ip);
                return user;
            } else if(User.USERSTATE_UNACTIVE.equals(user.getState())){
                throw new ServiceException("该账号未激活");
            } else {
                throw new ServiceException("该账号已被禁用");
            }
        }else {
            throw new ServiceException("账号或密码错误");
        }

    }

    /**
     * 根据token激活对应的用户
     * @param token
     */
    public void activeUser(String token) {
        String userName = cache.getIfPresent(token);
        if(userName==null){
            throw new ServiceException("token无效或已过期");
        } else {
            User user = userMapper.findByUserName(userName);
            if(user==null){
                throw new ServiceException("无法找到对应的账号");
            }else {
                user.setState(User.USERSTATE_ACTIVE);
                userMapper.update(user);

                //将缓存中的键值对删除
                cache.invalidate(token);
            }


        }
    }

    /**
     * 用户找回密码
     * @param sessionId 客户端的sessionId,限制客户端的操作频率
     * @param type 找回密码方式eamil/phone
     * @param value 电子邮件地址/手机号码
     */
    public void foundPassword(String sessionId, String type, String value) {
        if(activeCache.getIfPresent(sessionId)==null){
            if("phone".equals(type)){
                //TODO根据手机号码找回密码
            }else if("email".equals(type)){
                User user = userMapper.findByEmail(value);
                if(user!=null){
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String uuid = UUID.randomUUID().toString();
                            String url = "http://localhost/foundpassword/newpassword?token="+uuid;

                            passwordCache.put(uuid,user.getUserName());
                            String html = user.getUserName()+"<br>请点击该<a href='"+url+"'>链接</a>进行找回密码操作，链接在30分钟内有效";
                            EmailUtil.sendHtmlEmail(value,"密码找回邮件",html);

                        }
                    });
                    thread.start();
                }
            }
            activeCache.put(sessionId,"sdada");
        }else {
            throw new ServiceException("操作频率过快");
        }
    }

    public User foundPasswordGetUserByToken(String token) {
        String username = passwordCache.getIfPresent(token);
        if(StringUtils.isEmpty(username)){
            throw new ServiceException("token过期或错误");
        }else {
            User user = userMapper.findByUserName(username);
            if(user==null){
                throw new ServiceException("未找到对应账号");
            } else {
                return user;
            }
        }
    }

    /**
     * 充值用户的密码
     * @param id 用户ID
     * @param token 找回密码的Token
     * @param password 新密码
     */
    public void resetPassword(String id, String token, String password) {
        if(passwordCache.getIfPresent(token)==null){
            throw new ServiceException("token过期或错误");
        } else {
            User user = userMapper.findById(Integer.valueOf(id));
            user.setPassword(DigestUtils.md5Hex(Config.get("user.password.salt")+password));
            userMapper.update(user);
            logger.info("{}重置了密码",user.getUserName());
        }
    }

    /**
     * 修改用户的电子邮件
     * @param user
     * @param email
     */
    public void updateEmail(User user, String email) {
        user.setEmail(email);
        userMapper.update(user);
    }

    public void updatePassword(User user, String oldPassword, String newPassword) {
        String salt = Config.get("user.password.salt");
        System.out.println(DigestUtils.md5Hex(salt+oldPassword));
        if(DigestUtils.md5Hex(salt+oldPassword).equals(user.getPassword())){
            newPassword = DigestUtils.md5Hex(salt+newPassword);
            System.out.println(newPassword);
            user.setPassword(newPassword);
            userMapper.update(user);
        } else {
            throw new ServiceException("原始密码错误");
        }
    }

    public void updateAvatar(User user, String fileKey) {
        user.setAvatar(fileKey);
        userMapper.update(user);
    }

    /**
     *根据用户查找所有消息
     * @param user
     * @return
     */
    public List<Notify> findNotifyListByUser(User user) {
        return notifyMapper.findNotifyListByUser(user);
    }

    public Integer findUnReadCount(User user) {
        return notifyMapper.findUnReadCount(user);
    }

    public Page<UserVo> findUserList(Integer pageNo) {
        Integer count = userMapper.count();
        Page<UserVo> page = new Page<>(count,pageNo);
        List<User> userList = userMapper.findAllUsers(page);
        List<UserVo> userVoList = new ArrayList<>();

        for(User user:userList){
            UserVo userVo = userMapper.findUserVo(user.getId());
            userVoList.add(userVo);
        }
        page.setItems(userVoList);
        return page;
    }

    public void updateUserState(String userId, Integer userState) {
        if(StringUtils.isNumeric(userId)){
            User user = userMapper.findById(Integer.valueOf(userId));
            user.setState(userState);
            userMapper.update(user);
        }else{
            throw new ServiceException("参数异常");
        }
    }
}
