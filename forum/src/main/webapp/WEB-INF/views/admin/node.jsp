<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/29
  Time: 12:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>节点管理</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link href="/static/css/sweetalert.css" rel="stylesheet">
    <style>
        .mt20 {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<div class="container-fluid mt20">
    <a href="/admin/addNewNode" class="btn btn-success">添加新节点</a>
    <table class="table">
        <thead>
            <tr>
                <th>节点名称</th>
                <th>操作</th>
            </tr>
        </thead>
        <c:forEach items="${nodeList}" var="node">
            <tr>
                <td>${node.nodeName}</td>
                <td>
                    <a href="/updateNode?nodeId=${node.id}">修改</a>
                    <a href="javascript:;" hel="${node.id}" class="del">删除</a>
                </td>

            </tr>
        </c:forEach>
    </table>

<script src="/static/js/jquery-1.11.1.js"></script>
    <script src="/static/js/sweetalert.min.js"></script>
    <script>
        $(function () {
            $(".del").click(function () {
                var nodeId = $(this).attr("hel");
                swal({
                    title:"确定要删除该节点?",
                    type:"warning",
                    showCancelButton:true,
                    confirmButtonColor:"#DB6655",
                    confirmButtonText:"确认",
                    closeOnConfirm:false},
                function () {
                    $.ajax({
                        url:"/admin/delNode",
                        type:"post",
                        data:{"nodeId":nodeId},
                        success:function (data) {
                            if(data.state=="success"){
                                swal("删除成功");
                                window.href.go(0);
                            }
                        },
                        error:function () {
                            swal("服务器错误,删除节点失败");
                        }
                    })
                })
            })
        })
    </script>

</div>
</body>
</html>
