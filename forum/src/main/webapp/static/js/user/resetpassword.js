/**
 * Created by wtj on 2016/12/17.
 */
$(function () {
    $("#resetBtn").click(function () {
        alert("")
        $("#resetForm").submit();
    });
    $("#resetForm").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            password:{
                required:true,
                minlength:6
            },
            repassword:{
                required:true,
                equalTo:"#password"
            }
        },
        messages:{
            password:{
                required:"请输入密码",
                minlength:"密码长度不得小于6位"
            },
            repassword:{
                required:"请再次输入密码",
                equalTo:"两次密码不一致"
            }
        },
        submitHandler:function (form) {
            $.ajax({
                url:"/foundpassword/newpassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#resetBtn").text("保存中...").attr("disabled","disabled");
                },
                success:function (data) {
                    if(data.state == 'success') {
                        alert("密码重置成功,请登录");
                        window.location.href = "/login";
                    } else {
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#resetBtn").removeAttr("disabled");
                }
            })
        }

    })



})