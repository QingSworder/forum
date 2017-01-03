package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
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
@WebServlet("/updateNode")
public class UpdateNodeServlet extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        NodeService nodeService = new NodeService();
        Node node = nodeService.findNodeByNodeId(Integer.valueOf(nodeId));
        req.setAttribute("node",node);
        forwords("/admin/updateNode.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeId = req.getParameter("nodeId");
        String nodeName = req.getParameter("nodename");
        NodeService nodeService = new NodeService();
        JsonResult jsonResult = new JsonResult();
        try{
            nodeService.updateNodeById(nodeName,nodeId);
            jsonResult.setState("success");
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            throw new ServiceException(e.getMessage());
        }
        renderJson(jsonResult,resp);



    }
}
