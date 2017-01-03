package com.kaishengit.web.topic;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.entity.Comment;
import com.kaishengit.entity.Fav;
import com.kaishengit.entity.Topic;
import com.kaishengit.entity.User;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.TopicService;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wtj on 2016/12/21.
 */
@WebServlet("/topicDetail")
public class TopicDetailServlet extends BaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String topicId = req.getParameter("topicid");
        TopicService service = new TopicService();

        //判断该用户是否收藏该帖子，如果收藏页面显示取消收藏，如果未收藏，页面显示添加收藏
        //找到该用户搜藏的所有帖子Id
        User user = getCurrentUser(req);
        if(user!=null){
            List<Fav> favs = service.findAllFavByUserId(user.getId());
            if(favs!=null){
                for(Fav fav:favs){
                    if(fav.getTopicId().equals(topicId));
                    req.setAttribute("fav","fav");
                    break;
                }
            }
        }
        try{
            List<Comment> comments = service.findAllCommentsById(topicId);
            //System.out.println(comments.size());
            Topic topic = service.findTopicById(topicId);



            req.setAttribute("comments",comments);
            req.setAttribute("topic",topic);
            forwords("/topic/topicDetail.jsp",req,resp);
        }catch(ServiceException e){
            resp.sendError(404);
        }
    }


}
