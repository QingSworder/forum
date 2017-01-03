package com.kaishengit.web.user;

import com.google.common.collect.Maps;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by wtj on 2016/12/15.
 */
@WebServlet("/reg")
public class RegServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwords("user/reg.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得客户端传来的参数
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");

        Map<String,Object> result = Maps.newHashMap();

        try{
            UserService userService = new UserService();
            userService.save(userName,password,email,phone);
            result.put("state","success");
        }catch (Exception ex){
            ex.printStackTrace();
            result.put("state","error");
            result.put("message","注册失败,请稍后再试");
        }
        renderJson(result,resp);


    }
}
