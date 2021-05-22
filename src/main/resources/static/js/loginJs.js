//当页面加载完成之后
$(function () {
    //登录请求
    loginAjax();
    //当按下enter键时提交
    enterLoginDown();
});

//当按下enter键时登录
function enterLoginDown() {
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#btn_sub").click();
        }
    });
}

//登录请求
function loginAjax() {
    //给登录按钮绑定单击事件
    $("#btn_sub").click(function () {
        //发送ajax请求，提交表单数据
        $.post("/user/login", $("#loginForm").serialize(), function (data) {
            //处理响应结果
            if (data.flag) {
                //登录成功
                location.href = "/user/index";
            } else {
                //登录失败
                $("#errorMsg").html(data.errorMsg);
                $("#checkCodeButton").click();
            }
        });
    });
}

//图片点击事件
function changeCheckCode(img) {
    img.src = "/checkCode?" + new Date().getTime();
}
