package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/29.
 */
@WebServlet("/admin/addNewNode")
public class AddNewNodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        forwords("/admin/addNewNode.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodename");
        NodeService nodeService = new NodeService();
        JsonResult jsonResult = new JsonResult();
        try {
            nodeService.addNewNode(nodeName);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
        }
        renderJson(jsonResult,resp);
    }
}
