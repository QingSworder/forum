$(function () {
    $("#btn").click(function () {
        $("#form").submit();
    });
    $("#type").change(function () {
       var value = $(this).val();
       if("email"==value){
           $("#typename").text("电子邮件");
       } else if ("phone"==value){
           $("#typename").text("手机号码");
       }
    }),
    $("#form").validate({
        errorElement:'span',
        errorClass:'text-error',
        rules:{
            value:{
                required:true
            }
        },
        messages:{
            value:{
                required:"该项必填"
            }
        },
        submitHandler:function () {
            $.ajax({
                url:"/foundpassword",
                type:"post",
                data:$(form).serialize(),
                beforeSend:function () {
                    $("#btn").text("提交中...").attr("disabled");
                },
                success:function (data) {
                    if(data.state=="success"){
                        var type= $("#type").val();
                        if("email"==type){
                            alert("请查收邮件进行操作");
                        }else {
                            //TODO电话的提示
                        }
                    }else{
                        alert(data.message);
                    }
                },
                error:function () {
                    alert("服务器错误");
                },
                complete:function () {
                    $("#btn").text("提交").removeAttr("disabled");
                }
            })
        }
    });


})