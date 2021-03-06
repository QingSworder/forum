package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wtj on 2016/12/15.
 */
@WebServlet("/login")
public class LoginServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断当前是否有用户
        req.getSession().removeAttribute("curr_user");
        forwords("user/login.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        System.out.println(userName);
        //获取客户端的ip地址
        String ip = req.getRemoteAddr();

        Map<String,Object> result = Maps.newHashMap();
        UserService userService = new UserService();

        try{
            User user = userService.login(userName,password,ip);
            //经登录成功的用户放入Session
            HttpSession session = req.getSession();
            session.setAttribute("curr_user",user);

            result.put("state","success");
        }catch (ServiceException ex){
            result.put("state","error");
            result.put("message",ex.getMessage());
        }

        renderJson(result,resp);
    }
}
