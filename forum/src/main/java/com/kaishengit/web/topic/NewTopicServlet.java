package com.kaishengit.web.topic;

import com.google.common.collect.Maps;
import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Node;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Config;
import com.kaishengit.web.BaseServlet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wtj on 2016/12/21.
 */
@WebServlet("/newTopic")
public class NewTopicServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Auth auth = Auth.create(Config.get("qiniu.ak"),Config.get("qiniu.sk"));
        StringMap stringMap = new StringMap();
        stringMap.put("returnBody","{ \"success\": true,\"file_path\": \""+Config.get("qiniu.domain")+"${key}\"}");
        String token = auth.uploadToken(Config.get(("qiniu.buket")),null,3600,stringMap);
        //获取nodeList到jsp页面

        TopicService service = new TopicService();
        List<Node> nodeList = service.findAllNodes();

        req.setAttribute("token",token);
        req.setAttribute("nodeList",nodeList);
        forwords("/topic/newTopic.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收客户端传来的内容
        String title = req.getParameter("title");
        String nodeId = req.getParameter("nodeId");
        String content = req.getParameter("content");

        User user = getCurrentUser(req);
        TopicService topicService = new TopicService();
        Topic topic = topicService.addNewTopic(title, content, Integer.valueOf(nodeId), user.getId());


        JsonResult jsonResult = new JsonResult(topic);
        renderJson(jsonResult,resp);
    }
}
