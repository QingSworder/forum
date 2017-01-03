<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/23
  Time: 22:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>发布新主题</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    //<link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<!--header-bar end-->
<div class="container">
    <div class="box">
        <div class="box-header">
            <span class="title"><i class="fa fa-plus"></i>编辑主题</span>
        </div>

        <form action="" style="padding: 20px" id="sendForm">
            <label class="control-label">主题标题</label>
            <input name="topicId" id="topicId" type="hidden" value="${topic.id}">
            <input type="text" name="title" id="title" style="width: 100%;box-sizing: border-box;height: 30px" value="${topic.title}" placeholder="请输入主题标题，如果标题能够表达完整内容，则正文可以为空">
            <label class="control-label">正文</label>
            <textarea name="content" id="editor">${topic.content}</textarea>

            <select name="nodeId" id="nodeId" style="margin-top:15px;">
                <option value="">请选择节点</option>
                <c:forEach items="${nodeList}" var="node">
                    <option ${topic.node.id == node.id?'selected':''} value="${node.id}">${node.nodeName}</option>
                </c:forEach>
            </select>

        </form>
        <div class="form-actions" style="text-align: right">
            <button type="button" class="btn btn-primary" id="sendBtn">发布主题</button>
        </div>


    </div>
    <!--box end-->
</div>
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/simditor-emoji.js"></script>

<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor'),
            //optional options
            toolbar: ['title','bold','italic','underline','strikethrough',
                'fontScale','color','ol' ,'ul', 'blockquote','code',
                'table', 'image','emoji'],
            emoji: {
                imagePath: '/static/img/emoji/',
                images: ['+1.png',
                    '100.png',
                    '109.png']


            },
            upload:{
                url: 'http://up-z1.qiniu.com/',
                params:{"token":"${token}"},
                fileKey:'file'
            },
        });
        $("#sendBtn").click(function () {
            $("#sendForm").submit();
        });
        $("#sendForm").validate({
            errorElement:'span',
            errorClass:'text-error',
            rules:{
                title:{
                    required:true
                },
                nodeId:{
                    required:true
                }
            },
            messages:{
                title:{
                    required:'请输入标题'
                },
                nodeId:{
                    required:'请选择节点'
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:'/editTopic',
                    type:'post',
                    data:$(form).serialize(),
                    beforeSend:function () {
                        $("#sendBtn").text("发布中...").attr("disabled","disabled");
                    },
                    success:function (json) {
                        if(json.state=="success"){
                            window.location.href="/topicDetail?topicid="+json.data;
                        }else {
                            alert("发布失败，请稍后再试");
                        }
                    },
                    error:function () {
                        alert("服务器错误")
                    },
                    complete:function () {
                        $("#sendBtn").text("发布主题").remove("disabled");
                    }
                })
            }
        })

    });
</script>

</body>
</html>
