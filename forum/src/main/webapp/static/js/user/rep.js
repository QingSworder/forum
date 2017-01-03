/**
 * Created by wtj on 2016/12/15.
 */
$(function () {
    $("#logBtn").click(function () {
        $("#logForm").submit();
    });

    $("#logForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            userName:{
                required:true,
                minlength:3,
                remote:"/validate/user"
            },
            password:{
                required:true,
                rangelength:[6,18],
              },
            repassword:{
                required:true,
                rangelength:[6,18],
                equalTo:"#password"
            },
            email:{
                required:true,
                email:true,
                remote:"/validate/email"
            },
            phone:{
                required:true,
                rangelength:[11,11],
                digits:true
            }
        },
        messages: {
            userName: {
                required: "请输入账号",
                minlength: "账号至少为三个字符",
                remote: "账号已被占用"
            },
            password: {
                required: "请输入密码",
                rangelength: "密码长度为6到18个字符"
            },
            repassword: {
                required: "请再次输入密码",
                rangelength: "密码长度为6到18个字符",
                equalTo: "两次密码不一致"
            },
            email: {
                required: "请输入电子邮件",
                email: "邮件格式错误",
                remote: "电子邮件已被占用"
            },
            phone: {
                required: "请输入手机号码",
                rangelength: "手机号码格式错误",
                digits: "手机号码格式错误"
            }

        },
        submitHandler:function () {
            $.ajax({
                url:"/reg",
                type:"post",
                data:$("#logForm").serialize(),
                beforeSend:function () {
                    $("#logBtn").text("注册中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state=='success'){
                        alert("注册成功,邮箱激活账号");
                        window.location.href="/login";
                    }else{
                        alert(data.message);
                    }
                },
                 error:function() {
                     alert("服务器错误")
                 },
                 complete:function () {
                     $("#logBtn").text("注册").removeAttr("disabled");
                 }
            });

        }

    })

})