//当页面加载完成之后
$(function () {

    $.get("/user/findOne", {}, function (data) {
        let uid;
        let rName = getParameter("rName");
        //判断rName如果不为null或者""
        if (rName) {
            //url解码
            rName = window.decodeURIComponent(rName);
        }
        if (data) {
            uid = data.uid;
            load(uid, "", rName);
        }
    })

});
//分页功能
function load(uid, currentPage, rName) {
    //发送ajax请求，请求route/pageQuery,传递cid
    $.get("/route/FavoritesPageQuery", {uid: uid, currentPage: currentPage, rName: rName}, function (pb) {

        //解析pagebean数据，展示到页面上

        //1.分页工具条数据展示
        //1.1 展示总页码和总记录数
        $("#totalPage").html(pb.totalPage);
        $("#totalCount").html(pb.totalCount);

        let lis = "";

        let fristPage = '<li style="cursor: pointer" onclick="javascipt:load(' + uid + ',1,\'' + rName + '\')">首页</li>';

        //计算上一页的页码
        let beforeNum = pb.currentPage - 1;
        if (beforeNum <= 0) {
            beforeNum = 1;
        }

        let beforePage = '<li style="cursor: pointer"  onclick="javascipt:load(' + uid + ',' + beforeNum + ',\'' + rName + '\')" class="threeword">上一页</li>';

        lis += fristPage;
        lis += beforePage;
        //1.2 展示分页页码
        /*
            1.一共展示10个页码，能够达到前5后4的效果
            2.如果前边不够5个，后边补齐10个
            3.如果后边不足4个，前边补齐10个
        */

        // 定义开始位置begin,结束位置 end
        let begin; // 开始位置
        let end; //  结束位置


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


        for (let i = begin; i <= end; i++) {
            let li;
            //判断当前页码是否等于i
            if (pb.currentPage == i) {

                li = '<li style="cursor: pointer" class="curPage" onclick="javascipt:load(' + uid + ',' + i + ',\'' + rName + '\')">' + i + '</li>';

            } else {
                //创建页码的li
                li = '<li style="cursor: pointer" onclick="javascipt:load(' + uid + ',' + i + ',\'' + rName + '\')">' + i + '</li>';
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

        let lastPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + uid + ',' + pb.totalPage + ',\'' + rName + '\')">末页</li>';
        let nextPage = '<li style="cursor: pointer" class="threeword" onclick="javascipt:load(' + uid + ',' + nextPageCount + ',\'' + rName + '\')">下一页</li>';

        lis += nextPage;
        lis += lastPage;


        //将lis内容设置到 ul
        $("#pageNum").html(lis);

        //2.列表数据展示
        let route_lis = "";

        if (pb.list) {
            for (let i = 0; i < pb.list.length; i++) {
                //获取{rid:1,rName:"xxx"}
                let route = pb.list[i];

                let li = '<div class="col-md-3">\n' +
                    '                                    <a href="/user/routeDetail?rid=' + (route.rid) + '">\n' +
                    '                                        <img src=' + "/" + route.rimage + ' alt="">\n' +
                    '                                        <div class="has_border">\n' +
                    '                                            <h3>' + route.rname + '</h3>\n' +
                    '                                            <div class="price">网付价<em>￥</em><strong>' + route.price + '</strong><em>起</em></div>\n' +
                    '                                        </div>\n' +
                    '                                    </a>\n' +
                    '                                </div>'

                route_lis += li;
            }
        }
        $(".row").html(route_lis);

        //定位到页面顶部
        window.scrollTo(0, 0);
    });

}
