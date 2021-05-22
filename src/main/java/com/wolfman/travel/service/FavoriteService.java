package com.wolfman.travel.service;


import com.wolfman.travel.bean.Favorite;

import java.util.Date;
import java.util.List;

public interface FavoriteService {

    /**
     * 判断用户是否收藏过该路线
     *
     * @param rid
     * @param uid
     * @return
     */
    Object isFavorite(String rid, String uid);

    /**
     * 添加收藏
     *
     * @param rid
     * @param uid
     * @param date
     * @return
     */
    boolean add(String rid, int uid, Date date);

    /**
     * 删除收藏
     *
     * @param rid
     * @param uid
     */
    void delete(String rid, int uid);

    /**
     * 查找用户所有收藏的路线
     *
     * @param uid
     * @return
     */
    List<Favorite> findFavorites(String uid);
}
