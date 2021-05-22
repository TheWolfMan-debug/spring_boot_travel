package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.Category;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CategoryMapper {

    /**
     * 查询所有路线
     *
     * @return
     */
    @Select("select * from tab_category")
    List<Category> findAll();
}
