package com.kaishengit.web;
import com.google.gson.Gson;
import com.kaishengit.entity.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by wtj on 2016/12/15.
 */
public class BaseServlet extends HttpServlet{
    public void forwords(String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/"+path).forward(request,response);
    }

    /**
     * 响应客户端
     * @param str
     * @param response
     * @throws IOException
     */
    public void renderText(String str,HttpServletResponse response) throws IOException {
        response.setContentType("text/plain;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(str);
        printWriter.flush();
        printWriter.close();
    }
    public void renderJson(Object obj,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter printWriter = response.getWriter();
        printWriter.print(new Gson().toJson(obj));
        printWriter.flush();
        printWriter.close();
    }
    public User getCurrentUser(HttpServletRequest request){
        HttpSession session = request.getSession();
        if(session.getAttribute("curr_user")==null){
            return null;
        } else {
            return (User)session.getAttribute("curr_user");
        }
    }
}
