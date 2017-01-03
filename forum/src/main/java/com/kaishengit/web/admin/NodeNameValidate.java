package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.NodeService;
import com.kaishengit.web.BaseServlet;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/29.
 */
@WebServlet("/admin/nodeNameValidate")
public class NodeNameValidate extends BaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nodeName = req.getParameter("nodename");
        nodeName = com.kaishengit.util.StringUtils.isoToUtf8(nodeName);
        String type = req.getParameter("type");
        String nodeId = req.getParameter("nodeId");
        NodeService nodeService = new NodeService();

        if(StringUtils.isNumeric(type)&&(1==Integer.valueOf(type))){
            Node node = nodeService.findNodeByNodeId(Integer.valueOf(nodeId));
            if(node!=null){
                if(nodeName.equals(node.getNodeName())){
                    renderText("true",resp);
                }else {
                    Node node1 = nodeService.findNodeByNodeName(nodeName);
                    if(node1!=null){
                        renderText("false",resp);
                    }else {
                        renderText("true",resp);
                    }
                }
                return;
            }
        }
        nodeName = com.kaishengit.util.StringUtils.isoToUtf8(nodeName);



        Node node = nodeService.findNodeByNodeName(nodeName);
        if(node!=null){
            renderText("false",resp);
        }else {
            renderText("true",resp);
        }
    }
}
