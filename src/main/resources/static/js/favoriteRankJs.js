//当页面加载完成之后
$(function () {
    //分页功能
    load(null, "", null, null);
    //分页搜索功能
    searchHandler();
    //当按下enter键时提交
    enterSearchButtonDown();
});

//当按下enter键时提交
function enterSearchButtonDown() {
    //提交数据
    $("#routeName").focus(function () {
        searchRankSubmit();
    });
    $("#lowPrice").focus(function () {
        searchRankSubmit();
    });
    $("#highPrice").focus(function () {
        searchRankSubmit();
    });
}

//提交数据
function searchRankSubmit() {
    $(document).keydown(function (event) {
        if (event.keyCode == 13) {
            $("#searchButton").click();
        }
    });
}

//分页搜索功能
function searchHandler() {
    $("#searchButton").click(function () {
        let routeName = $("#routeName").val();
        let lowPrice = $("#lowPrice").val();
        let highPrice = $("#highPrice").val();

        if (lowPrice === "") {
            lowPrice = null;
        }
        if (highPrice === "") {
            highPrice = null;
        }

        load(null, routeName, lowPrice, highPrice);
    });
}

//分页功能
function load(currentPage, rName, lowPrice, highPrice) {
    //发送ajax请求，请求route/pageQuery,传递cid
    $.get("/route/FavoritesRankPageQuery", {
        currentPage: currentPage,
        rName: rName,
        lowPrice: lowPrice,
        highPrice: highPrice
    }, function (pb) {

        //解析pagebean数据，展示到页面上

        //1.分页工具条数据展示
        //1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);

        var lis = "";

        var fristPage = '<li style="cursor: pointer" onclick="javascipt:load(1,\'' + rName + '\',' + lowPrice + ',' + highPrice + ')">首页</li>';

        //计算上一页的页码
        var beforeNum = pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }

        var beforePage = '<li style="cursor: pointer" onclick="javascipt:load(' + beforeNum + ',\'' + rName + '\',' + lowPrice + ',' + highPrice + ')" class="threeword">上一页</li>';

        lis += fristPage;
        lis += beforePage;
        //1.2 展示分页页码
        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
        */

        // 定义开始位置begin,结束位置 end
        var begin; // 开始位置
        var end; //  结束位置


        //1.要显示10个页码
        if (pb.totalPage < 10) {
            //总页码不够10页

            begin = 1;
            end = pb.totalPage;
        } else {
            //总页码超过10页

            begin = pb.currentPage - 5;
            end = pb.currentPage + 4;

            //2.如果前边不够5个，后边补齐10个
            if (begin < 1) {
                begin = 1;
                end = begin + 9;
            }

            //3.如果后边不足4个，前边补齐10个
            if (end > pb.totalPage) {
                end = pb.totalPage;
                begin = end - 9;
            }
        }


        for (var i = begin; i <= end; i++) {
            var li;
            //判断当前页码是否等于i
            if (pb.currentPage == i) {

                li = '<li style="cursor: pointer" class="curPage" onclick="javascipt:load(' + i + ',\'' + rName + '\',' + lowPrice + ',' + highPrice + ')">' + i + '</li>';

            } else {
                //创建页码的li
                li = '<li style="cursor: pointer" onclick="javascipt:load(' + i + ',\'' + rName + '\',' + lowPrice + ',' + highPrice + ')">' + i + '</li>';
            }
            //拼接字符串
            lis += li;
        }


        var nextPageCount;
        if (pb.currentPage == pb.totalPage) {
            nextPageCount = pb.totalPage;
        } else {
            nextPageCount = pb.currentPage + 1;
        }

        var lastPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + pb.totalPage + ',\'' + rName + '\',' + lowPrice + ',' + highPrice + ')">末页</li>';
        var nextPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + nextPageCount + ',\'' + rName + '\',' + lowPrice + ',' + highPrice + ')">下一页</li>';

        lis += nextPage;
        lis += lastPage;


        //将lis内容设置到 ul
        $("#pageNum").html(lis);

        //2.列表数据展示
        var route_lis = "";

        if (pb.list) {
            for (var i = 0; i < pb.list.length; i++) {
                //获取{rid:1,rName:"xxx"}
                var route = pb.list[i];
                var li;
                if (i < 2 && (currentPage === null || parseInt(currentPage) === 1)) {
                    currentPage = 1;
                    li = '<li>\n' +
                        '                <span class="num one">' + (i + 1 + (currentPage - 1) * 8) + '</span>\n' +
                        '                <a href="/user/routeDetail?rid=' + (route.rid) + '"><img src=' + "/" + route.rimage + ' alt=""></a>\n' +
                        '                <h4><a href="/user/routeDetail?rid=' + (route.rid) + '">' + route.rname + '</a></h4>\n' +
                        '                <p>\n' +
                        '                    <b class="price">&yen;<span>' + route.price + '</span>起</b>\n' +
                        '                    <span class="shouchang">已收藏' + route.count + '次</span>\n' +
                        '                </p>\n' +
                        '            </li>'
                    route_lis += li;
                } else {
                    currentPage = parseInt(currentPage);
                    li = '<li>\n' +
                        '                <span class="num ">' + (i + 1 + (currentPage - 1) * 8) + '</span>\n' +
                        '                <a href="/user/routeDetail?rid=' + (route.rid) + '"><img src=' + "/" + route.rimage + ' alt=""></a>\n' +
                        '                <h4><a href="/user/routeDetail?rid=' + (route.rid) + '">' + route.rname + '</a></h4>\n' +
                        '                <p>\n' +
                        '                    <b class="price">&yen;<span>' + route.price + '</span>起</b>\n' +
                        '                    <span class="shouchang">已收藏' + route.count + '次</span>\n' +
                        '                </p>\n' +
                        '            </li>'
                    route_lis += li;
                }
            }
        }
        $("#favoritesRank").html(route_lis);

        //定位到页面顶部
        window.scrollTo(0, 0);
    });

}


