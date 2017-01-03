<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/30
  Time: 8:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>用户管理</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
</head>
<%@include file="../include/adminNavbar.jsp" %>
<body>
<div class="container-fluid">
    <table class="table">
        <thead>
        <tr>
            <th>用户名</th>
            <th>注册时间</th>
            <th>最后登录时间</th>
            <th>最后登录ip</th>
            <th>账户状态</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${page.items}" var="userVo">
            <tr>
                <td>${userVo.userName}</td>
                <td>${userVo.createTime}</td>
                <td>${userVo.lastLoginTime}</td>
                <td>${userVo.loginIp}</td>
                <td>
                    <a href="javascript:;" class="update" onclick="update(${userVo.userId},${userVo.userState})"
                       rel="${userVo.userState} ${userVo.userId}">${userVo.userState == '1'?'禁用':'恢复'}</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="pagination pagination-mini pagination-centered ">
    <ul id="pagination" style="margin-bottom: 20px"></ul>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.twbsPagination.min.js"></script>
<script>
    $(function(){
        $("#pagination").twbsPagination({
            totalPages:${page.totalPage},
            visiblePages:5,
            first:'首页',
            last:'末页',
            prev:'上一页',
            next:'下一页',
            href: '?p={{number}}'
        });
    });

        function update(userId,userState){
            $.post("/admin/userManage",{"userId":userId,"userState":userState},function(json){
                if(json.state=='success'){
                    alert("修改成功");
                    window.history.go(0);
                }else{
                    alert(json.message)
                }
            });
        }
</script>
</body>
</html>
