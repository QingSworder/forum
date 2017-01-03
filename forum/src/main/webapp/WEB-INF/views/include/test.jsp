<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/28
  Time: 9:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>sss<input type="text" id="test"></div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script>
    /*$(function () {
        $("#test").keydown(function (event) {
            alert(event.keyCode)
        })
    })*/
    window.document.getElementById("test").onkeydown = function (event) {
        alert(event.keyCode);
    }
</script>
</body>
</html>
