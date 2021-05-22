//当页面加载完成之后
$(function () {

    //查询用户信息
    findUserInfo();
    //查询分类数据
    findCategory();
    //给搜索按钮绑定单击事件，获取搜索输入框的内容
    searchSubmit();
    //当按下enter键时登录
    enterHeaderSearchDown();

});

//当按下enter键时登录
function enterHeaderSearchDown() {
    $("#search_input").focus(function (){
        $(document).keydown(function (event) {
            if (event.keyCode == 13) {
                $("#search-button").click();
            }
        });
    })

}

//给搜索按钮绑定单击事件，获取搜索输入框的内容
function searchSubmit() {
    $("#search-button").click(function () {
        //线路名称
        let rName = $("#search_input").val();
        let cid = getParameter("cid");
        if (!cid) {
            cid = 0;
        }
        location.href = "/user/routeList?currentPage=&cid=" + cid + "&rName=" + rName;

    });
}

//查询分类数据
function findCategory() {

    $.get("/category/findAll", {}, function (data) {
        //[{cid:1,cname:国内游},{},{}]
        let lis = '<li class="nav-active"><a href="/user/index">首页</a></li>';
        //遍历数组,拼接字符串(<li>)
        for (let i = 0; i < data.length; i++) {

            // let li = '<li><a href="route_list.html?cid='+data[i].cid+'">'+data[i].cname+'</a></li>';
            let li = '<li><a href="/user/routeList?currentPage=&cid=' + data[i].cid + '&rName=">' + data[i].cname + '</a></li>';
            lis += li;
        }

        lis += '<li><a href="/user/favoriteRank">收藏排行榜</a></li>';

        //将lis字符串，设置到ul的html内容中
        $("#category").html(lis);
    });
}

//查询用户信息
function findUserInfo() {

    $.get("/user/findOne", {}, function (user) {
        if (user.username != null) {
            let msg = "欢迎回来，" + user.username;
            $("#span_username").html(msg);
        }
    });
}