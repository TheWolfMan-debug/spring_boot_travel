//当页面加载完成之后
$(function () {
    //引入头部页面
    $.get("header.html",function (data) {
        $("#header").html(data);
    });
    //引入尾部页面
    $.get("footer.html",function (data) {
        $("#footer").html(data);
    });
});