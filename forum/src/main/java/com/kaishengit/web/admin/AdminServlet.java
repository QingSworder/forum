package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Admin;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/28.
 */
@WebServlet("/admin/login")
public class AdminServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //判断当前是否有用户登录
        req.getSession().removeAttribute("curr_admin");
        forwords("/admin/login.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取客户端传来的账户密码
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        String ip = req.getRemoteAddr();
        AdminService adminService = new AdminService();
        JsonResult jsonResult = new JsonResult();
        try {
            Admin admin = adminService.findAdminByUserName(userName,password,ip);
            req.getSession().setAttribute("curr_admin",admin);
            jsonResult.setState(JsonResult.SUCCESS);
        } catch(ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(e.getMessage());
        }

        renderJson(jsonResult,resp);


    }
}

