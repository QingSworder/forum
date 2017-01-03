package com.kaishengit.web.topic;

import com.kaishengit.entity.Notify;
import com.kaishengit.entity.User;
import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wtj on 2016/12/27.
 */
@WebServlet("/notify")
public class NotifyServlet extends BaseServlet {
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //拿到当前用户
        User user = getCurrentUser(req);
        //查找该用户所发帖子的回帖
        List<Notify> notifyList = userService.findNotifyListByUser(user);
        //拿到该用户未读的消息数目
        Integer unReadCount = userService.findUnReadCount(user);
        req.setAttribute("unReadCount",unReadCount);
        req.setAttribute("notifyList",notifyList);
        forwords("/topic/notify.jsp",req,resp);
    }
}
