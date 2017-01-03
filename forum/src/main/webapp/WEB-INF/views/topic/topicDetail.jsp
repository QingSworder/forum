<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/21
  Time: 13:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>主题页</title>
    <link href="http://cdn.bootcss.com/font-awesome/4.5.0/css/font-awesome.min.css" rel="stylesheet">
    <link href="http://cdn.bootcss.com/bootstrap/2.3.1/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/style.css">
    <link rel="stylesheet" href="/static/js/editer/styles/simditor.css">
    <style>
        body{
            background-image: url(/static/img/bg.jpg);
        }
        .simditor .simditor-body {
            min-height: 100px;
        }
        .topic-body img {
            width: 200px;
        }
        pre {
            padding: 0px;
            border: 0px;
            background-color: transparent;
        }
    </style>
</head>
<body>
<%@include file="../include/navbar.jsp"%>
<div class="container">
    <div class="box">
        <ul class="breadcrumb" style="background-color: #fff;margin-bottom: 0px;">
            <li><a href="/home">首页</a><span class="divider"></span></li>
            <li class="active">${requestScope.topic.node.nodeName}</li>
        </ul>
        <div class="topic-head">
            <img class="img-rounded avatar" src="http://oigngl0pa.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView/1/w/20/h/20  " alt="">
            <h3 class="title">${requestScope.topic.title}</h3>
            <p class="topic-msg muted"><a href="">${requestScope.topic.user.userName}</a><span id="topicTime">${requestScope.topic.createTime}</span></p>
        </div>
        <div class="topic-body">
            ${requestScope.topic.content}
        </div>
        <div class="topic-toolbar">
            <ul class="unstyled inline pull-left">
                <c:choose>
                    <c:when test="${not empty fav}">
                        <li><a href="javascript:;" id="favTopic">取消收藏</a></li>
                    </c:when>
                    <c:otherwise>
                        <li><a href="javascript:;" id="favTopic">加入收藏</a></li>
                    </c:otherwise>
                </c:choose>

                <li><a href="">感谢</a></li>
                <c:if test="${topic.user.id == sessionScope.curr_user.id and topic.edit}">
                    <li><a href="/editTopic?topicid=${topic.id}">编辑</a></li>
                </c:if>
            </ul>
            <ul class="unstyled inline pull-right muted">
                <li>${requestScope.topic.clickNum}点击</li>
                <li>${requestScope.topic.favNum}人收藏</li>
                <li>${requestScope.topic.thankNum}人感谢</li>
            </ul>
        </div>
    </div>
    <!--box end-->
    <c:if test="${not empty comments}">
    <div class="box" style="margin-top:20px">
        <div class="talk-item muted" style="margin-top:20px">
                ${fn:length(comments)}个回复 | 直到 <span id="lastReplyTime">${topic.lastReplyTime}</span>
        </div>
        <c:forEach items="${comments}" var="reply" varStatus="vs">
            <div class="talk-item">
                <table class="talk-table">
                    <tr>
                        <a href="" name="reply${vs.count}"></a>
                        <td width="50">
                            <img class="avatar" src="http://oigngl0pa.bkt.clouddn.com/${sessionScope.curr_user.avatar}?imageView/1/w/20/h/20" alt="">
                        </td>
                        <td width="auto">
                            <a href="" style="font-size: 12px">${reply.user.userName}</a> <span style="font-size: 12px" class="reply">${reply.createTime}</span>
                            <br>
                            <p style="font-size: 14px">${reply.content}</p>
                        </td>
                        <td width="70" align="right" style="font-size: 12px">
                            <a href="javascript:;" title="回复" rel="${vs.count}" class="replyLink"><i class="fa fa-reply"></i></a>&nbsp;
                            <span class="badge">${vs.count}</span>
                        </td>
                    </tr>
                </table>
            </div>
        </c:forEach>
    </c:if>
    <c:choose>
        <c:when test="${not empty sessionScope.curr_user}">
            <div class="box" style="margin:20px 0px;">
                <a name="reply"></a>
                <div class="talk-item muted" style="font-size: 12px"><i class="fa fa-plus"></i> 添加一条新回复</div>
                <form action="/newReply?topicid=${requestScope.topic.id}" method="post" style="padding: 15px;margin-bottom:0px;" id="replyForm">
                    <textarea name="content" id="editor"></textarea>
                </form>
                <div class="talk-item muted" style="text-align: right;font-size: 12px">
                    <span class="pull-left">请尽量让自己的回复能够对别人有帮助回复</span>
                    <button type="button" class="btn btn-primary" id="replyBtn">发布</button>
                </div>
            </div>
        </c:when>
        <c:otherwise>
            <div class="box" style="margin:20px 0px;">
                <div class="talk-item"> 请<a href="/login?redirect=topicDetail?topicid=${topic.id}#reply">登录</a>后再回复</div>
            </div>
        </c:otherwise>
    </c:choose>

    </div>
</div>
<!--container end-->
<script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
<script src="/static/js/editer/scripts/module.min.js"></script>
<script src="/static/js/editer/scripts/hotkeys.min.js"></script>
<script src="/static/js/editer/scripts/uploader.min.js"></script>
<script src="/static/js/editer/scripts/simditor.min.js"></script>
<script src="/static/js/highlight.pack.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/moment.min.js"></script>
<script src="//cdn.bootcss.com/moment.js/2.10.6/locale/zh-cn.js"></script>
<script>
    $(function(){
        var editor = new Simditor({
            textarea: $('#editor'),
            toolbar:false
            //optional options
        });
        $("#replyBtn").click(function () {
            $("#replyForm").submit();
        });
        $("#favTopic").click(function () {
            var $this = $(this);
            var action = '';
            if($this.text()=="加入收藏"){
                action = 'fav';
            } else {
                action = 'unfav';
            }
            $.post("/favTopic",{"topicId":${topic.id},"action":action}).done(function (json) {
                if(json.state=="success"){
                    if($this.text()=="取消收藏"){
                        $this.text("加入收藏");
                    }else {$this.text("取消收藏")}
                    window.history.go(0);
                }
                $("#topicFav").text(json.data);
            }).error(function () {
            })
        });

        $("#topicTime").text(moment($("#topicTime").text()).fromNow());

        $("#lastReplyTime").text(moment($("#lastReplyTime").text()).format("YYYY年MM月DD日 HH:mm:ss"));
        
        $(".reply").text(function () {
            var time = $(this).text();
            return moment(time).fromNow();
        });

        $(".replyLink").click(function () {
            var count = $(this).attr("rel");
            var html = "<a href='#reply"+count+"'>#"+ count +"</a>";
            editor.setValue(html + editor.getValue());
            window.location.href="#reply";
        })
        hljs.initHighLightingOnLoad();

    });
</script>

</body>
</html>

