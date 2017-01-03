package com.kaishengit.web.admin;

import com.kaishengit.entity.Node;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wtj on 2016/12/28.
 */
@WebServlet("/admin/node")
public class AdminNodeServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //找到所有节点传到后台
        AdminService adminService = new AdminService();
        List<Node> nodeList = adminService.findAllNodes();
        req.setAttribute("nodeList",nodeList);
        forwords("/admin/node.jsp",req,resp);
    }
}
