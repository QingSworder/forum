package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.dao.UserDao;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wtj on 2016/12/19.
 */
@WebServlet("/setting")
public class SettingServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //计算七牛云的token
       Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
       String token = auth.uploadToken(Config.get("qiniu.buket"));

        req.setAttribute("token",token);
        forwords("/user/setting.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if("profile".equals(action)){
            updateProfile(req,resp);
        } else if("password".equals(action)){
            updatePassword(req,resp);
        } else if("avatar".equals(action)){
            updateAvatar(req,resp);
        }
            
    }

    private void updateAvatar(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String fileKey = req.getParameter("fileKey");
        System.out.println(fileKey);
        User user = getCurrentUser(req);

        UserService userService = new UserService();
        Map<String,Object> result = Maps.newHashMap();

        try{
            userService.updateAvatar(user,fileKey);
            result.put("state","success");
            renderJson(result,resp);
        }catch (ServiceException e){
            result.put("state","error");
            result.put("message",e.getMessage());
            renderJson(result,resp);
        }



    }

    private void updatePassword(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String oldPassword = req.getParameter("oldpassword");
        String newPassword = req.getParameter("newpassword");
        //System.out.println(oldPassword+"->"+newPassword);
        User user = getCurrentUser(req);
        System.out.println(user.getPassword());
        UserService userService = new UserService();
        Map<String,Object> result = Maps.newHashMap();
        try{
            userService.updatePassword(user,oldPassword,newPassword);
            result.put("state","success");

            renderJson(result,resp);
        } catch(ServiceException e){
            result.put("state","error");
            result.put("message",e.getMessage());
            renderJson(result,resp);
        }


    }

    private void updateProfile(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameter("email");
        User user = getCurrentUser(req);

        UserService userService = new UserService();
        userService.updateEmail(user,email);
        Map<String,Object> result = Maps.newHashMap();
        result.put("state","success");
        renderJson(result,resp);
    }
}
