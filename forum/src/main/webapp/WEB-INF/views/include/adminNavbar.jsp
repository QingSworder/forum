<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/28
  Time: 13:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="navbar navbar-inverse  navbar-static-top">
    <div class="navbar-inner">
        <a class="brand" href="/admin/home?_=0">BBS 管理系统</a>
        <ul class="nav">
            <li class="${param._=='0'?'active':''}"><a href="/admin/home?_=0">首页</a></li>
            <li class="${param._=='1'?'active':''}"><a href="/admin/topic?_=1">主题管理</a></li>
            <li class="${param._=='2'?'active':''}"><a href="/admin/node?_=2">节点管理</a></li>
            <li class="${param._=='3'?'active':''}"><a href="/admin/userManage?_=3">用户管理</a></li>
        </ul>
        <ul class="nav pull-right">
            <li><a href="/admin/loginOut">安全退出</a></li>
        </ul>
    </div>
</div>

</body>
</html>
