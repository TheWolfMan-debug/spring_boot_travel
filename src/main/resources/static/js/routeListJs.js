//当页面加载完成之后
$(function () {
    //解析参数并调用分页功能
    parseParameter();
});

//解析参数并调用分页功能
function parseParameter() {
    //获取cid的参数值
    let cid = getParameter("cid");
    //获取rname的参数值
    let rname = getParameter("rname");
    //判断rname如果不为null或者""
    if (rname) {
        //url解码
        rname = window.decodeURIComponent(rname);
    }
    //当页码加载完成后，调用load方法，发送ajax请求加载数据
    load(cid, null, rname);
}

//分页功能
function load(cid, currentPage, rname) {
    //发送ajax请求，请求route/pageQuery,传递cid
    $.get("/user/routeList/page", {cid: cid, currentPage: currentPage, rName: rname}, function (pb) {
        //解析pagebean数据，展示到页面上
        //1.分页工具条数据展示
        //1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);
        let lis = "";
        let fristPage = '<li style="cursor: pointer" onclick="javascipt:load(' + cid + ',1,\'' + rname + '\')">首页</li>';
        //计算上一页的页码
        let beforeNum = pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }
        let beforePage = '<li style="cursor: pointer" onclick="javascipt:load(' + cid + ',' + beforeNum + ',\'' + rname + '\')" class="threeword">上一页</li>';
        lis += fristPage;
        lis += beforePage;
        //1.2 展示分页页码
        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
        */
        //定义开始位置begin,结束位置 end
        let begin; // 开始位置
        let end; //  结束位置
        //要显示10个页码
        if (pb.totalPage < 10) {
            //总页码不够10页
            begin = 1;
            end = pb.totalPage;
        } else {
            //总页码超过10页
            begin = pb.currentPage - 5;
            end = pb.currentPage + 4;
            //如果前边不够5个，后边补齐10个
            if (begin < 1) {
                begin = 1;
                end = begin + 9;
            }
            //如果后边不足4个，前边补齐10个
            if (end > pb.totalPage) {
                end = pb.totalPage;
                begin = end - 9;
            }
        }
        for (let i = begin; i <= end; i++) {
            let li;
            //判断当前页码是否等于i
            if (pb.currentPage == i) {
                li = '<li style="cursor: pointer" class="curPage" onclick="javascipt:load(' + cid + ',' + i + ',\'' + rname + '\')">' + i + '</li>';
            } else {
                //创建页码的li
                li = '<li style="cursor: pointer" onclick="javascipt:load(' + cid + ',' + i + ',\'' + rname + '\')">' + i + '</li>';
            }
            //拼接字符串
            lis += li;
        }

        let nextPageCount;
        if (pb.currentPage == pb.totalPage) {
            nextPageCount = pb.totalPage;
        } else {
            nextPageCount = pb.currentPage + 1;
        }
        let lastPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + cid + ',' + pb.totalPage + ',\'' + rname + '\')">末页</li>';
        let nextPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + cid + ',' + nextPageCount + ',\'' + rname + '\')">下一页</li>';
        lis += nextPage;
        lis += lastPage;
        //将lis内容设置到 ul
        $("#pageNum").html(lis);
        //列表数据展示
        let route_lis = "";

        for (let i = 0; i < pb.list.length; i++) {
            //获取{rid:1,rname:"xxx"}
            let route = pb.list[i];

            let li = '<li>\n' +
                '                        <div class="img"><a href="/user/routeDetail?rid=' + route.rid + '" ><img src="/' + route.rimage + '" style="width: 299px;"></a></div>\n' +
                '                        <div class="text1">\n' +
                '                            <p>' + route.rname + '</p>\n' +
                '                            <br/>\n' +
                '                            <p>' + route.routeIntroduce + '</p>\n' +
                '                        </div>\n' +
                '                        <div class="price">\n' +
                '                            <p class="price_num">\n' +
                '                                <span>&yen;</span>\n' +
                '                                <span>' + route.price + '</span>\n' +
                '                                <span>起</span>\n' +
                '                            </p>\n' +
                '                            <p><a href="/user/routeDetail?rid=' + route.rid + '" >查看详情</a></p>\n' +
                '                        </div>\n' +
                '                    </li>';
            route_lis += li;
        }
        $("#route").html(route_lis);
        //定位到页面顶部
        window.scrollTo(0, 0);
    });

}

