/**
 * Created by wtj on 2016/12/19.
 */
$(function () {
    $("#basicBtn").click(function () {
        $("#basicForm").submit();
    });
    $("#basicForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            email:{
                required:true,
                email:true,
                remote:"/validate/email?type=1"
            }
        },
        messages: {
            email:{
                required: "请输入电子邮件",
                email: "邮件格式错误",
                remote: "此邮件已被使用"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/setting?action=profile",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#basicBtn").text("保存中").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("修改成功")
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#basicBtn").text("保存").removeAttr("disabled");
                }
            })
        }

    });
    //密码修改
    $("#passwordBtn").click(function () {
        $("#passwordForm").submit();
    });
    $("#passwordForm").validate({
        errorElements:'span',
        errorClass:'text-error',
        rules:{
            oldpassword:{
                required:true,
                rangelength:[6,18]
            },
            newpassword:{
                required:true,
                rangelength:[6,18]
            },
            repassword:{
                required:true,
                equalTo:"#newpassword"
            }
        },
        messages:{
            oldpassword:{
                required:"请输入当前密码",
                rangelength:"密码长度为6到18位"
            },
            newpassword:{
                required:"请输入新密码",
                rangelength:"密码长度为6到18位"
            },
            repassword:{
                required:"请再次确认新密码",
                equalTo:"两次密码不一致"
            }
        },
        submitHandler:function(form){
            $.ajax({
                url:"/setting?action=password",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#passwordBtn").text("保存中").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        alert("保存成功");
                        window.location.href="/login";
                    }
                },
                error:function () {
                    alert("修改失败");
                },
                complete:function () {
                    $("#passwordBtn").text("保存").removeAttr("disabled");
                }
            })
        }

    })





})