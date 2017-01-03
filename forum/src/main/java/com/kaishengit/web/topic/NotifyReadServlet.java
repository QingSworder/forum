package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/27.
 */
@WebServlet("/notifyRead")
public class NotifyReadServlet extends BaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //1.接受客户端传来的已阅读消息ids字符串
        String ids = req.getParameter("ids");
        //2.字符串转换为数组
        String[] idS = ids.split(",");

        TopicService topicService = new TopicService();
        topicService.readNotify(idS);
        JsonResult jsonResult = new JsonResult(JsonResult.SUCCESS);
        renderJson(jsonResult,resp);

    }
}
