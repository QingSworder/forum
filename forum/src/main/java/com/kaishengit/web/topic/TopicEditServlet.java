package com.kaishengit.web.topic;
import com.kaishengit.dao.NodeDao;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wtj on 2016/12/23
 */
@WebServlet("/editTopic")
public class TopicEditServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取客户端传来的topicId
        String topicId = req.getParameter("topicid");
        TopicService topicService = new TopicService();
        Topic topic = topicService.findTopicById(topicId);

        //获取所有节点
        NodeDao nodeDao = new NodeDao();
        List<Node> nodeList = nodeDao.findAllNodes();

        req.setAttribute("topic",topic);
        req.setAttribute("nodeList",nodeList);

        //跳转到编辑页面
        forwords("topic/editTopic.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String nodeId = req.getParameter("nodeId");
        String topicId = req.getParameter("topicId");


        JsonResult jsonResult = null;

        if(StringUtils.isNumeric(topicId)){
            TopicService topicService = new TopicService();
            try{
                topicService.updateTopicById(title,content,nodeId,topicId);
                jsonResult = new JsonResult();
                jsonResult.setState(JsonResult.SUCCESS);
                jsonResult.setData(topicId);
            }catch (ServiceException e){
                jsonResult.setMessage(e.getMessage());
            }
        }else {
            jsonResult = new JsonResult("参数异常");
        }

        renderJson(jsonResult,resp);
    }
}
