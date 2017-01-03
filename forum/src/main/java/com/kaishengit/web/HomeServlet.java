package com.kaishengit.web;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wtj on 2016/12/15.
 */
@WebServlet("/home")
public class HomeServlet extends BaseServlet {
    TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //老师方法
        String nodeId = req.getParameter("nodeId");
        String p = req.getParameter("p");

        Integer pageNo = StringUtils.isNumeric(p)? Integer.valueOf(p):1;

        if(!StringUtils.isEmpty(nodeId)&&!StringUtils.isNumeric(nodeId)){
            forwords("/home.jsp",req,resp);
            return;
        }

        TopicService topicService = new TopicService();
        List<Node> nodeList = topicService.findAllNodes();

        Page<Topic> page = topicService.findAllTopics(nodeId,pageNo);
        req.setAttribute("page",page);
        req.setAttribute("nodeList",nodeList);
        forwords("/home.jsp",req,resp);


        //笨方法
        /*//拿到所有节点
        List<Node> nodeList = topicService.findAllNodes();


        List<Topic> topicList = null;
        if(nodeId!=null){
            //拿到各个节点对应板块帖子
            topicList = topicService.findTopicsByNodeId(Integer.valueOf(nodeId));
        }else {
            //显示所有帖子
            topicList = topicService.findAllTopics();
        }

        req.setAttribute("nodeList",nodeList);
        req.setAttribute("topicList",topicList);

        forwords("/home.jsp",req,resp);*/
    }
}
