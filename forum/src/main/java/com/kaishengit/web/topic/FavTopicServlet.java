package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/24.
 */
@WebServlet("/favTopic")
public class FavTopicServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicId");
        String action = req.getParameter("action");

        TopicService topicService = new TopicService();
        JsonResult jsonResult = null;
        User user = getCurrentUser(req);
        try{
            topicService.favTopicOrNot(topicId,user,action);
            jsonResult = new JsonResult();
            jsonResult.setState("success");
        }catch (ServiceException e){
            throw new ServiceException("收藏帖子异常");
        }
        renderJson(jsonResult,resp);
    }
}
