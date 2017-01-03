package com.kaishengit.web.admin;

import com.kaishengit.entity.TopicReplyCount;
import com.kaishengit.service.TopicService;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/28.
 */
@WebServlet("/admin/home")
public class AdminHomeServlet extends BaseServlet {
    TopicService topicService = new TopicService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)? Integer.valueOf(p):1;

        Page<TopicReplyCount> page = topicService.getDayTopicAndReplyNumList(pageNo);
        req.setAttribute("page",page);
        forwords("/admin/home.jsp",req,resp);
    }
}