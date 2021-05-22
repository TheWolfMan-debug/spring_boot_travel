package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.RouteImg;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface RouteImgMapper {

    /**
     * 根据route的id查询图片
     *
     * @param rid
     * @return
     */
    @Select("select * from tab_route_img where rid = #{rid}")
    List<RouteImg> findByRid(int rid);
}
