package com.kaishengit.web.admin;

import com.kaishengit.dto.JsonResult;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.service.UserService;
import com.kaishengit.util.Page;
import com.kaishengit.util.StringUtils;
import com.kaishengit.vo.UserVo;
import com.kaishengit.web.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by wtj on 2016/12/30.
 */
@WebServlet("/admin/userManage")
public class AdminUserServlet extends BaseServlet {
    UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //查找所有用户最后登录时间以及账户状态并分页显示
        String p = req.getParameter("p");
        Integer pageNo = StringUtils.isNumeric(p)? Integer.valueOf(p):1;

        Page<UserVo> page = userService.findUserList(pageNo);
        req.setAttribute("page",page);
        forwords("/admin/userManage.jsp",req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer userState = Integer.valueOf(req.getParameter("userState"));
        String userId = req.getParameter("userId");
        userState = userState == 1 ? 2:1;
        JsonResult jsonResult = new JsonResult();
        try {
            userService.updateUserState(userId, userState);
            jsonResult.setState(JsonResult.SUCCESS);
        }catch (ServiceException e){
            jsonResult.setState(JsonResult.ERROR);
            jsonResult.setMessage(e.getMessage());
        }
        renderJson(jsonResult,resp);
    }

}
