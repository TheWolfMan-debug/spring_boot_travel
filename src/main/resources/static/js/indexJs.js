//当页面加载完成之后
$(function () {

    //填充数据
    findAllRoutes();

});

//填充数据
function findAllRoutes() {
    $.get("/user/findAllRoutes", {}, function (routesData) {
        let id;
        let popularity = '';
        let newest = '';
        let theme = '';
        let domesticTour = '';
        let overseasTour = '';
        for (var i = 0; i < 4; i++) {
            id = Math.floor(Math.random() * 1000) % 513;
            popularity += '<div class="col-md-3">\n' +
                '                                    <a href="/user/routeDetail?rid=' + routesData[id].rid + '">\n' +
                '                                        <img src=' + "/" + routesData[id].rimage + ' alt="">\n' +
                '                                        <div class="has_border">\n' +
                '                                            <h3>' + routesData[id].routeIntroduce + '</h3>\n' +
                '                                            <div class="price">网付价<em>￥</em><strong>' + routesData[id].price + '</strong><em>起</em></div>\n' +
                '                                        </div>\n' +
                '                                    </a>\n' +
                '                                </div>'

        }
        for (var i = 0; i < 4; i++) {
            id = Math.floor(Math.random() * 1000) % 513;
            newest += '<div class="col-md-3">\n' +
                '                                    <a href="/user/routeDetail?rid=' + routesData[id].rid + '">\n' +
                '                                        <img src=' + "/" + routesData[id].rimage + ' alt="">\n' +
                '                                        <div class="has_border">\n' +
                '                                            <h3>' + routesData[id].routeIntroduce + '</h3>\n' +
                '                                            <div class="price">网付价<em>￥</em><strong>' + routesData[id].price + '</strong><em>起</em></div>\n' +
                '                                        </div>\n' +
                '                                    </a>\n' +
                '                                </div>'

        }
        for (var i = 0; i < 4; i++) {
            id = Math.floor(Math.random() * 1000) % 513;
            theme += '<div class="col-md-3">\n' +
                '                                    <a href="/user/routeDetail?rid=' + routesData[id].rid + '">\n' +
                '                                        <img src=' + "/" + routesData[id].rimage + ' alt="">\n' +
                '                                        <div class="has_border">\n' +
                '                                            <h3>' + routesData[id].routeIntroduce + '</h3>\n' +
                '                                            <div class="price">网付价<em>￥</em><strong>' + routesData[id].price + '</strong><em>起</em></div>\n' +
                '                                        </div>\n' +
                '                                    </a>\n' +
                '                                </div>'

        }
        for (var i = 0; i < 6; i++) {
            id = Math.floor(Math.random() * 1000) % 513;
            domesticTour += '<div class="col-md-4">\n' +
                '                                    <a href="/user/routeDetail?rid=' + routesData[id].rid + '">\n' +
                '                                        <img src=' + "/" + routesData[id].rimage + ' alt="">\n' +
                '                                        <div class="has_border">\n' +
                '                                            <h3>' + routesData[id].routeIntroduce + '</h3>\n' +
                '                                            <div class="price">网付价<em>￥</em><strong>' + routesData[id].price + '</strong><em>起</em></div>\n' +
                '                                        </div>\n' +
                '                                    </a>\n' +
                '                                </div>'

        }
        for (var i = 0; i < 6; i++) {
            id = Math.floor(Math.random() * 1000) % 513;
            overseasTour += '<div class="col-md-4">\n' +
                '                                    <a href="/user/routeDetail?rid=' + routesData[id].rid + '">\n' +
                '                                        <img src=' + "/" + routesData[id].rimage + ' alt="">\n' +
                '                                        <div class="has_border">\n' +
                '                                            <h3>' + routesData[id].routeIntroduce + '</h3>\n' +
                '                                            <div class="price">网付价<em>￥</em><strong>' + routesData[id].price + '</strong><em>起</em></div>\n' +
                '                                        </div>\n' +
                '                                    </a>\n' +
                '                                </div>'


        }
        $("#popularity").html(popularity);
        $("#newest").html(newest);
        $("#theme").html(theme);
        $("#domesticTour").html(domesticTour);
        $("#overseasTour").html(overseasTour);

    });
}