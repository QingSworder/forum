package com.kaishengit.web.user;

import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/17.
 */
@WebServlet("/user/active")
public class ActiveServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("_");
        if(StringUtils.isEmpty(token)){
            resp.sendError(404);
        } else {
            UserService userService = new UserService();
            try{
                userService.activeUser(token);
                forwords("user/active_success.jsp",req,resp);
            } catch (ServiceException ex){
                req.setAttribute("message",ex.getMessage());
                forwords("user/active_error.jsp",req,resp);
            }
        }
    }
}
