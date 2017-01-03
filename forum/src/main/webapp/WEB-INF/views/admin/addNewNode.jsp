<%--
  Created by IntelliJ IDEA.
  User: wtj
  Date: 2016/12/29
  Time: 13:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>添加新节点</title>
    <link rel="stylesheet" href="/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="/static/css/sweetalert.css">
</head>
<body>
<%@include file="../include/adminNavbar.jsp"%>
<div class="container-fluid" style="margin-top: 20px">
    <form action="" id="updateForm">
        <legend>添加节点</legend>
        <lable>节点名称</lable>
        <input type="text" name="nodename">
        <div class="form-actions">
            <button id="saveBtn" class="btn-primary" type="button">保存</button>
        </div>
    </form>
</div>
<script src="/static/js/jquery-1.11.1.js"></script>
<script src="/static/js/jquery.validate.min.js"></script>
<script src="/static/js/sweetalert.min.js"></script>
<script>
    $(function () {
        $("#saveBtn").click(function () {
            $("#updateForm").submit();
        });
        $("#updateForm").validate({
            errorElement:'span',
            errorClass:'text-error',
            rules:{
                nodename:{
                    required:true,
                    remote:"/admin/nodeNameValidate"
                }
            },
            messages:{
                nodename:{
                    required:'请输入新节点名字',
                    remote:'节点名字已存在'
                }
            },
            submitHandler:function (form) {
                $.ajax({
                    url:"/admin/addNewNode",
                    type:"post",
                    data:$(form).serialize(),
                    success:function (json) {
                        if(json.state=="success"){
                            swal({title:"添加成功"},function () {
                                window.location.href = "/admin/node";
                            })
                        }else {
                            swal(json.message);
                        }
                    },
                    error:function () {
                        swal("添加失败，服务器错误");
                    }
                })
            }

        })
    })
</script>

</body>
</html>
