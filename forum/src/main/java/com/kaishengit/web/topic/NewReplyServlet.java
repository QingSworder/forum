package com.kaishengit.web.topic;

import com.kaishengit.entity.User;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/22.
 */
@WebServlet("/newReply")
public class NewReplyServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicid");
        //获取客户端评论内容
        String content = req.getParameter("content");
        User user = getCurrentUser(req);
        TopicService service = new TopicService();

        if (StringUtils.isNumeric(topicId)){
            service.addTopicReply(topicId,content,user);

        }else {
            resp.sendError(404);
        }
        resp.sendRedirect("/topicDetail?topicid="+topicId);

    }
}
