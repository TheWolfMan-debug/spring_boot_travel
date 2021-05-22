//定义收藏的点击事件
let isUserFavorite = false;

//当页面加载完成之后
$(function () {
    //填充数据
    fillData();
    //用户是否收藏
    isFavorite();
    //点击收藏按钮
    clickFavorite();
    //点击切换图片
    goImg();

});

//点击切换图片
function goImg() {
    //焦点图效果
    //点击图片切换图片
    $('.little_img').on('mousemove', function () {
        $('.little_img').removeClass('cur_img');
        var big_pic = $(this).data('bigpic');
        $('.big_img').attr('src', big_pic);
        $(this).addClass('cur_img');
    });
    //上下切换
    var picindex = 0;
    var nextindex = 4;
    $('.down_img').on('click', function () {
        var num = $('.little_img').length;
        if ((nextindex + 1) <= num) {
            $('.little_img:eq(' + picindex + ')').hide();
            $('.little_img:eq(' + nextindex + ')').show();
            picindex = picindex + 1;
            nextindex = nextindex + 1;
        }
    });
    $('.up_img').on('click', function () {
        var num = $('.little_img').length;
        if (picindex > 0) {
            $('.little_img:eq(' + (nextindex - 1) + ')').hide();
            $('.little_img:eq(' + (picindex - 1) + ')').show();
            picindex = picindex - 1;
            nextindex = nextindex - 1;
        }
    });
}

//自动轮播方法
function auto_play() {
    var cur_index = $('.prosum_left dd').find('a.cur_img').index();
    cur_index = cur_index - 1;
    var num = $('.little_img').length;
    var max_index = 3;
    if ((num - 1) < 3) {
        max_index = num - 1;
    }
    if (cur_index < max_index) {
        var next_index = cur_index + 1;
        var big_pic = $('.little_img:eq(' + next_index + ')').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(' + next_index + ')').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    } else {
        var big_pic = $('.little_img:eq(0)').data('bigpic');
        $('.little_img').removeClass('cur_img');
        $('.little_img:eq(0)').addClass('cur_img');
        $('.big_img').attr('src', big_pic);
    }
}

//填充数据
function fillData() {
    //1.获取rid
    let rid = getParameter("rid");
    //2.发送请求请求 route/findOne

    $.get("/user/routeDetailFindOne", {rid: rid}, function (route) {
        //3.解析数据填充html
        $("#rname").html(route.rname);
        $("#routeIntroduce").html(route.routeIntroduce);
        $("#price").html("¥" + route.price);
        $("#sname").html(route.seller.sname);
        $("#consphone").html(route.seller.consphone);
        $("#address").html(route.seller.address);
        //设置收藏次数
        $("#favoriteCount").html("已收藏" + route.count + "次");
        //设置第一张图片
        $("#firstImg").attr("src",'/'+route.routeImgList[0].bigPic)
        //图片展示
        var ddstr = '<a class="up_img up_img_disable"></a>';
        //遍历routeImgList
        for (var i = 0; i < route.routeImgList.length; i++) {
            var astr;
            if (i >= 4) {
                astr = '<a title="" class="little_img" data-bigpic="'+'/' + route.routeImgList[i].bigPic + '" style="display:none;">\n' +
                    '                        <img src="'+'/' + route.routeImgList[i].smallPic + '">\n' +
                    '                    </a>';
            } else {
                astr = '<a title="" class="little_img" data-bigpic="'+'/' + route.routeImgList[i].bigPic + '">\n' +
                    '                        <img src="'+'/' + route.routeImgList[i].smallPic + '">\n' +
                    '                    </a>';
            }
            ddstr += astr;
        }
        ddstr += '<a class="down_img down_img_disable" style="margin-bottom: 0;"></a>';
        $("#dd").html(ddstr);
        //图片展示和切换代码调用
        goImg();
    });
}

//判断用户是否收藏过路线
function isFavorite() {
    // 发送请求，判断用户是否收藏过该线路
    var rid = getParameter("rid");
    $.get("/route/isFavorite", {rid: rid}, function (flag) {
        if (flag === "true" || flag === true) {
            // 用户已经收藏过
            //设置收藏按钮的样式
            $("#favorite").addClass("already");

            $("#clickFavorite").html("已收藏");
            isUserFavorite = true;
        } else {
            // 用户没有收藏
            $("#favorite").removeClass("already");
            $("#clickFavorite").html("点击收藏");
            isUserFavorite = false;
        }
    });

}

//收藏的点击事件
function clickFavorite() {
    $("#favorite").click(function () {
        $.get("/user/findOne", {}, function (user) {
            if (user != null && "" != user) {
                if (isUserFavorite) {
                    deleteFavorite();
                    isUserFavorite = false;
                } else {
                    addFavorite();
                    isUserFavorite = true;
                }
            } else {
                window.location.href = "/user/login";
            }
        });
    })
}

//点击添加收藏按钮触发的方法
function addFavorite() {
    var rid = getParameter("rid");
    //用户登录了
    $.get("/route/addFavorite", {rid: rid}, function () {
        //代码刷新页面
        isFavorite();
        fillData();
    });
}

//点击取消收藏按钮触发的方法
function deleteFavorite() {
    var rid = getParameter("rid");
    //取消功能
    $.get("/route/deleteFavorite", {rid: rid}, function () {
        isFavorite();
        fillData();
    });
}

