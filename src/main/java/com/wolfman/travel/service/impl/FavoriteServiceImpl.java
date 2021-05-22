package com.wolfman.travel.service.impl;


import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.mapper.FavoriteMapper;
import com.wolfman.travel.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FavoriteServiceImpl implements FavoriteService {

    @Autowired
    FavoriteMapper favoriteMapper;

    @Autowired
    @Qualifier("cacheManager")
    RedisCacheManager redisCacheManager;


    /**
     * 判断用户是否收藏过该路线
     *
     * @param rid
     * @param uid
     * @return
     */
    @Override
    //将查询结果放入缓存,key设置为("#uid +'-->'+ #rid"),保证键唯一
    @Cacheable(value = {"isFavorite"}, key = ("#uid +'-->'+ #rid"))
    //返回Object对象的原因:从缓存中查出为字符串，程序返回为boolean类型
    public Object isFavorite(String rid, String uid) {
        Favorite favorite = null;
        if (rid != null && !"".equals(rid)) {
            //调用findByRidAndUid查询
            favorite = favoriteMapper.findByRidAndUid(rid, uid);
        }
        return favorite != null;//如果对象有值，则为true，反之，则为false
    }

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     * @param date
     * @return
     */
    @Override
    @Caching(evict = {
            //更新缓存中的数据
            @CacheEvict(value = {"Favorites"}, key = "#uid"),
            @CacheEvict(value = {"Route"}, key = "#rid")},
            put = {
                    //将添加的结果放入缓存,保持键一致
                    @CachePut(value = {"isFavorite"}, key = ("#uid +'-->'+ #rid"))
            })
    public boolean add(String rid, int uid, Date date) {
        try {
            favoriteMapper.add(rid, uid, date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 删除收藏
     *
     * @param rid
     * @param uid
     */
    @Override
    @Caching(evict = {
            //更新Favorites缓存中的数据
            @CacheEvict(value = {"Favorites"}, key = "#uid"),
            //更新isFavorite缓存中的数据
            @CacheEvict(value = {"isFavorite"}, beforeInvocation = true, key = ("#uid +'-->'+ #rid")),
            //更新Route缓存中的数据
            @CacheEvict(value = {"Route"}, key = "#rid")
    })
    public void delete(String rid, int uid) {
        favoriteMapper.delete(rid, uid);
    }

    /**
     * 查找用户所有收藏的路线
     *
     * @param uid
     * @return
     */
    @Override
    //将查询结果放入缓存
    @Cacheable(value = {"Favorites"}, key = "#uid")
    public List<Favorite> findFavorites(String uid) {
        return favoriteMapper.findFavoritesByUid(uid);
    }
}
