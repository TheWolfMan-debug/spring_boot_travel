package com.wolfman.travel.controller;

import com.wolfman.travel.Component.PageComponent;
import com.wolfman.travel.Component.UserInfoComponent;
import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;
import com.wolfman.travel.bean.User;
import com.wolfman.travel.service.impl.FavoriteServiceImpl;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Controller
public class RouteController {

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    FavoriteServiceImpl favoriteService;

    @Autowired
    PageComponent pageComponent;

    @Autowired
    UserInfoComponent userInfoComponent;


    /**
     * 判断当前路线用户是否已收藏
     *
     * @param rid
     * @param request
     * @return
     */
    @ResponseBody
    @GetMapping("/route/isFavorite")
    public Object routeIsFavorite(String rid, HttpServletRequest request) {
        //获取当前登录的用户 user
        User user = userInfoComponent.findOneUser(request);
        String uid = String.valueOf(user.getUid());
        //favoriteService
        Object flag = favoriteService.isFavorite(rid, uid);
        return flag;
    }

    /**
     * 添加收藏
     *
     * @param rid
     * @param request
     * @return
     */
    @GetMapping("/route/addFavorite")
    @ResponseBody
    public boolean addFavorite(String rid, HttpServletRequest request) {
        // 获取当前登录的用户
        User user = userInfoComponent.findOneUser(request);
        int uid = user.getUid();//用户id
        //查询该地址收藏数量
        int favoriteCount = routeService.findCount(rid);
        // 将数量+1
        favoriteCount++;
        //更新数据库数据
        routeService.update(rid, favoriteCount);
        // 添加favorite表中的信息
        favoriteService.add(rid, uid, new Date());
        return true;
    }

    /**
     * 取消收藏
     *
     * @param rid
     * @param request
     * @return
     */
    @GetMapping("/route/deleteFavorite")
    @ResponseBody
    public boolean deleteFavorite(String rid, HttpServletRequest request) {
        // 获取当前登录的用户
        User user = userInfoComponent.findOneUser(request);
        int uid = user.getUid();//用户id
        //查询该地址收藏数量
        int favoriteCount = routeService.findCount(rid);
        //将数量+1
        favoriteCount--;
        routeService.update(rid, favoriteCount);
        //删除favorite表中的信息
        favoriteService.delete(rid, uid);
        return true;
    }


    /**
     * 路线分页
     *
     * @param cid
     * @param currentPage
     * @param rName
     * @param pageSize
     * @return
     */
    @ResponseBody
    @GetMapping("/user/routeList/page")
    public PageBean<Route> routeListPageQuery(String cid, String currentPage, String rName, String pageSize) {
        //获取路线cid，解析为int类型
        int cidInt = pageComponent.dataTransfer(cid, 0);
        //当前页码，如果不传递，则默认为第一页，解析为int类型
        int currentPageInt = pageComponent.dataTransfer(currentPage, 1);
        //每页显示条数，如果不传递，默认每页显示5条记录，解析为int类型
        int pageSizeInt = pageComponent.dataTransfer(pageSize, 5);
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.pageQuery(cidInt, currentPageInt, pageSizeInt, rName);
        return pb;
    }

    /**
     * 我的收藏分页
     *
     * @param uid
     * @param currentPage
     * @param rName
     * @param pageSize
     * @return
     */
    @GetMapping("/route/FavoritesPageQuery")
    @ResponseBody
    public PageBean<Route> FavoritesPageQuery(String uid, String currentPage, String rName, String pageSize) {
        //调用favoriteService查出用户收藏
        List<Favorite> favorites = favoriteService.findFavorites(uid);
        //当前页码，如果不传递，则默认为第一页
        int currentPageInt = pageComponent.dataTransfer(currentPage, 1);
        //每页显示条数，如果不传递，默认每页显示8条记录
        int pageSizeInt = pageComponent.dataTransfer(pageSize, 8);
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.FavoritesPageQuery(currentPageInt, pageSizeInt, favorites, rName);

        return pb;
    }


    /**
     * 排行榜分页功能
     *
     * @param currentPage
     * @param lowPrice
     * @param highPrice
     * @param pageSize
     * @param rName
     * @return
     */
    @ResponseBody
    @GetMapping("/route/FavoritesRankPageQuery")
    public PageBean<Route> FavoritesRankPageQuery(String currentPage, String lowPrice, String highPrice, String pageSize, String rName) {
        //当前页码，如果不传递，则默认为第一页
        int currentPageInt = pageComponent.dataTransfer(currentPage, 1);
        //每页显示条数，如果不传递，默认每页显示8条记录
        int pageSizeInt = pageComponent.dataTransfer(pageSize, 8);
        //调用service查询PageBean对象
        PageBean<Route> pb = routeService.FavoritesRankPageQuery(currentPageInt, pageSizeInt, rName, lowPrice, highPrice);
        return pb;
    }


}
