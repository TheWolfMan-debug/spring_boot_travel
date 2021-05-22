package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Favorite;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

public interface FavoriteMapper {

    /**
     * 根据rid和uid查询收藏信息
     *
     * @param rid
     * @param uid
     * @return
     */
    @Select("select * from tab_favorite where rid = #{rid} and uid = #{uid}")
    Favorite findByRidAndUid(String rid, String uid);

    /**
     * 根据rid查询收藏次数
     *
     * @param rid
     * @return
     */
    @Select("SELECT COUNT(*) FROM tab_favorite WHERE rid = #{rid}")
    int findCountByRid(int rid);


    /**
     * 添加我的收藏
     *
     * @param rid
     * @param uid
     * @param date
     */
    @Insert("insert into tab_favorite(rid,uid,date) values(#{rid},#{uid},#{date})")
    void add(String rid, int uid, Date date);

    /**
     * 根据uid查询收藏数据
     *
     * @param uid
     * @return
     */
    @Select("SELECT * FROM tab_favorite WHERE uid = #{uid}")
    List<Favorite> findFavoritesByUid(String uid);

    /**
     * 根据rid,uid删除收藏数据
     *
     * @param rid
     * @param uid
     */
    @Delete("delete from tab_favorite where rid = #{rid} and uid = #{uid}")
    void delete(String rid, int uid);
}
