package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Favorite;
import com.wolfman.travel.bean.Route;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface RouteMapper {

    /**
     * 查询所有路线
     *
     * @return
     */
    @Select("select * from tab_route")
    List<Route> findAll();

    /**
     * 根据路线id查询一条数据
     *
     * @param rid
     * @return
     */
    @Select("select * from tab_route where rid = #{rid}")
    Route findOne(int rid);

    /**
     * 根据目录cid,名称rNam查询路线
     *
     * @param cid
     * @param rName
     * @return
     */
    @Select({"<script>" +
            "select count(*) from tab_route " +
            "<where> " +
            "<if test='cid !=0'>" +
            "and cid=#{cid} " +
            "</if>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> " +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "</where>" +
            "</script>"})
    int findTotalCount(int cid, String rName);


    /**
     * 根据目录cid,名称rName进行分页查询路线
     *
     * @param cid
     * @param start
     * @param pageSize
     * @param rName
     * @return
     */
    @Select({"<script>" +
            "select * from tab_route " +
            "<where>" +
            "<if test='cid !=0'>" +
            "and cid= #{cid} " +
            "</if>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> " +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "</where>" +
            "limit #{start} , #{pageSize} " +
            "</script>"})
    List<Route> findByPage(int cid, int start, int pageSize, String rName);


    /**
     * 根据路线rid查询数据
     *
     * @param rid
     * @return
     */
    @Select("SELECT COUNT FROM tab_route WHERE rid=#{rid}")
    int findCount(String rid);

    @Select("UPDATE tab_route SET COUNT=#{favoriteCount} WHERE rid=#{rid}")
    void update(String rid, int favoriteCount);


    /**
     * 根据favorites,rName查询路线数量
     *
     * @param rName
     * @param favorites
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM tab_route " +
            "<where>" +
            "<when test='favorites != null and favorites.size() > 0'>" +
            "<foreach item='item' collection='favorites' open=' and rid in (' separator=',' close=') '>" +
            "#{item.rid}" +
            "</foreach>" +
            "</when>" +
            "<if test='rName !=\"\" and rName!=\"null\"'> " +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "</where>" +
            "</script>"})
    int findTotalCountByName(String rName, List<Favorite> favorites);


    /**
     * 根据favorites,rName进行分页查询
     *
     * @param start
     * @param pageSize
     * @param favorites
     * @param rName
     * @return
     */
    @Select({"<script>" +
            "SELECT * FROM tab_route " +
            "<where>" +
            "<when test='favorites != null and favorites.size() > 0'>" +
            "<foreach item='item' collection='favorites' open=' and rid in (' separator=',' close=') '>" +
            "#{item.rid}" +
            "</foreach>" +
            "</when>" +
            "<if test='rName !=\"\" and rName!=\"null\"'>" +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "</where>" +
            "limit #{start} , #{pageSize} " +
            "</script>"})
    List<Route> findFavoritesByPage(int start, int pageSize, List<Favorite> favorites, String rName);


    /**
     * @param rName
     * @param lowPrice
     * @param highPrice
     * @return
     */
    @Select({"<script>" +
            "SELECT COUNT(*) FROM tab_route " +
            "<where>" +
            "<if test='rName !=\"\" and rName!=\"null\"'>" +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "<if test='lowPrice !=\"\" and lowPrice!=\"null\"'> " +
            "and price > #{lowPrice} " +
            "</if>" +
            "<if test='highPrice !=\"\" and highPrice!=\"null\"'> " +
            "and #{highPrice} > price " +
            "</if>" +
            "</where>" +
            "</script>"})
    int findFavoritesRankTotalCount(String rName, String lowPrice, String highPrice);


    /**
     * 根据价格price,名称rName分页查询
     *
     * @param start
     * @param pageSize
     * @param rName
     * @param lowPrice
     * @param highPrice
     * @return
     */
    @Select({"<script>" +
            "SELECT * FROM tab_route " +
            "<where>" +
            "<if test='rName !=\"\" and rName!=\"null\"'>" +
            "and rname like concat('%',#{rName},'%') " +
            "</if>" +
            "<if test='lowPrice !=\"\" and lowPrice!=\"null\"'> " +
            "and price > #{lowPrice} " +
            "</if>" +
            "<if test='highPrice !=\"\" and highPrice!=\"null\"'> " +
            "and #{highPrice} > price " +
            "</if>" +
            "</where>" +
            "order by count desc limit #{start} , #{pageSize} " +
            "</script>"})
    List<Route> findFavoritesRankByPage(int start, int pageSize, String rName, String lowPrice, String highPrice);

}
