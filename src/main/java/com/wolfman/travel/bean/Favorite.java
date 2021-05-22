package com.wolfman.travel.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 收藏实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Favorite implements Serializable {

    private String rid;//旅游线路对象
    private String date;//收藏时间
    private String uid;//所属用户

}
