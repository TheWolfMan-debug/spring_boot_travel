package com.wolfman.travel.service;


import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.PageBean;
import com.wolfman.travel.bean.Route;

import java.util.List;

public interface RouteService {

    /**
     * 查询所有路线
     *
     * @return
     */
    List<Route> findAllRoutes();

    /**
     * 查询一条路线的所有信息
     *
     * @param rid
     * @return
     */
    Route findOne(int rid);

    /**
     * 路线的分页查询
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rName
     * @return
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rName);

    /**
     * 根据rid查询收藏次数
     *
     * @param rid
     * @return
     */
    int findCount(String rid);

    /**
     * 更新收藏次数
     *
     * @param rid
     * @param favoriteCount
     */
    void update(String rid, int favoriteCount);

    /**
     * 我的收藏分页查询
     *
     * @param currentPageInt
     * @param pageSizeInt
     * @param favorites
     * @param rName
     * @return
     */
    PageBean<Route> FavoritesPageQuery(int currentPageInt, int pageSizeInt, List<Favorite> favorites, String rName);

    /**
     * 收藏排行榜分页查询
     *
     * @param currentPageInt
     * @param pageSizeInt
     * @param rName
     * @param lowPrice
     * @param highPrice
     * @return
     */
    PageBean<Route> FavoritesRankPageQuery(int currentPageInt, int pageSizeInt, String rName, String lowPrice, String highPrice);
}
