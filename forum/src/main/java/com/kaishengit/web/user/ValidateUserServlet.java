package com.kaishengit.web.user;

import com.kaishengit.service.UserService;
import com.kaishengit.web.BaseServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.kaishengit.util.StringUtils;

/**
 * Created by wtj on 2016/12/15.
 */
@WebServlet("/validate/user")
public class ValidateUserServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("userName");
        //处理url的中文乱码问题
        username = com.kaishengit.util.StringUtils.isoToUtf8(username);

        UserService userService = new UserService();
        boolean result = userService.validateUserName(username);

        if(result){
            renderText("true",resp);
        }else{
            renderText("false",resp);
        }
    }
}
