<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/28
  Time: 13:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>用户管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<div class="container-fluid" style="margin-top:20px">
    <table class="table">
        <thead>
        <tr>
            <th>日期</th>
            <th>新主题数</th>
            <th>新回复数</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${page.items}" var="count">
                <tr>
                    <td>
                        ${count.time}
                    </td>
                    <td>${count.topicNum}</td>
                    <td>${count.replyNum}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="pagination pagination-centered pagination-mini">
        <ul id="pagination" style="margin-bottom: 20px"></ul>
    </div>
    <script src="/static/js/jquery-1.11.1.js"></script>
    <script src="/static/js/jquery.twbsPagination.min.js"></script>
    <script>
        $(function () {
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
    </script>
</div>
</body>
</html>
