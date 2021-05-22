package com.wolfman.travel.service.impl;

import com.wolfman.travel.Component.PageComponent;
import com.wolfman.travel.bean.*;
import com.wolfman.travel.mapper.FavoriteMapper;
import com.wolfman.travel.mapper.RouteImgMapper;
import com.wolfman.travel.mapper.RouteMapper;
import com.wolfman.travel.mapper.SellerMapper;
import com.wolfman.travel.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RouteServiceImpl implements RouteService {

    @Autowired
    RouteMapper routeMapper;

    @Autowired
    RouteImgMapper routeImgMapper;

    @Autowired
    SellerMapper sellerMapper;

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    PageComponent pageComponent;

    @Autowired
    @Qualifier("cacheManager")
    RedisCacheManager redisCacheManager;


    /**
     * 查询所有路线
     *
     * @return
     */
    @Override
    public List<Route> findAllRoutes() {
        return routeMapper.findAll();
    }

    /**
     * 查询一条路线的所有信息
     *
     * @param rid
     * @return
     */
    @Override
    //将查询信息放入缓存
    @Cacheable(value = {"Route"}, key = "#rid") //保证键唯一
    public Route findOne(int rid) {
        //根据id去route表中查询route对象
        Route route = routeMapper.findOne(rid);
        //根据route的id 查询图片集合信息
        List<RouteImg> routeImgList = routeImgMapper.findByRid(rid);
        //将集合设置到route对象
        route.setRouteImgList(routeImgList);
        //根据route的sid（商家id）查询商家对象
        Seller seller = sellerMapper.findById(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count = favoriteMapper.findCountByRid(rid);
        route.setCount(count);
        return route;
    }

    /**
     * 根据rid查询收藏次数
     *
     * @param rid
     * @return
     */
    @Override
    public int findCount(String rid) {
        return routeMapper.findCount(rid);
    }

    /**
     * 更新收藏次数
     *
     * @param rid
     * @param favoriteCount
     */
    @Override
    public void update(String rid, int favoriteCount) {
        routeMapper.update(rid, favoriteCount);
    }


    /**
     * 路线的分页查询
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rName
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rName) {
        //设置总记录数
        int totalCount = routeMapper.findTotalCount(cid, rName);
        //开始的记录数
        int start = (currentPage - 1) * pageSize;
        //查询路线
        List<Route> list = routeMapper.findByPage(cid, start, pageSize, rName);
        return pageComponent.pageUtil(currentPage, totalCount, list, pageSize);
    }

    /**
     * 我的收藏分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param favorites
     * @param rName
     * @return
     */
    @Override
    public PageBean<Route> FavoritesPageQuery(int currentPage, int pageSize, List<Favorite> favorites, String rName) {
        //如果为空
        if (favorites.size() == 0) {
            return pageComponent.pageUtil(currentPage, 0, null, pageSize);
        }
        //设置总记录数
        int totalCount = routeMapper.findTotalCountByName(rName, favorites);
        //开始的记录数
        int start = (currentPage - 1) * pageSize;//开始的记录数
        //查询路线
        List<Route> list = routeMapper.findFavoritesByPage(start, pageSize, favorites, rName);
        return pageComponent.pageUtil(currentPage, totalCount, list, pageSize);
    }

    /**
     * 收藏排行榜分页查询
     *
     * @param currentPage
     * @param pageSize
     * @param rName
     * @param lowPrice
     * @param highPrice
     * @return
     */
    @Override
    public PageBean<Route> FavoritesRankPageQuery(int currentPage, int pageSize, String rName, String lowPrice, String highPrice) {
        //设置总记录数
        int totalCount = routeMapper.findFavoritesRankTotalCount(rName, lowPrice, highPrice);
        //开始的记录数
        int start = (currentPage - 1) * pageSize;//开始的记录数
        List<Route> list = routeMapper.findFavoritesRankByPage(start, pageSize, rName, lowPrice, highPrice);
        //查询路线
        return pageComponent.pageUtil(currentPage, totalCount, list, pageSize);
    }

}
