package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.AdminService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/30.
 */
@WebServlet("/admin/delNode")
public class AdminDelNodeServlet extends BaseServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        AdminService adminService = new AdminService();
        JsonResult jsonResult = new JsonResult();
        try{
            adminService.deleteNodeById(Integer.valueOf(nodeId));
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            throw new ServiceException(e.getMessage());
        }
        renderJson(jsonResult,resp);

    }
}
