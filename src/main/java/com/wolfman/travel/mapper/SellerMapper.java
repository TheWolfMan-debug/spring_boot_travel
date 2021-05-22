package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Seller;
import org.apache.ibatis.annotations.Select;

public interface SellerMapper {
    /**
     * 根据id查询销售者
     *
     * @param id
     * @return
     */
    @Select("select * from tab_seller where sid = #{id}")
    Seller findById(int id);
}
