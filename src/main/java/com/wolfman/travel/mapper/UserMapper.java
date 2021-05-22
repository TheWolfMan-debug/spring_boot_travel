package com.wolfman.travel.mapper;

import com.wolfman.travel.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface UserMapper {

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Select("select * from tab_user where username = #{username}")
    User findByUsername(String username);

    /**
     * 用户保存
     *
     * @param user
     * @return
     */
    @Insert("insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values(#{username},#{password},#{name},#{birthday},#{sex},#{telephone},#{email},#{status},#{code})")
    void save(User user);

    /**
     * 根据激活码查询用户对象
     *
     * @param code
     * @return
     */
    @Select("select * from tab_user where code = #{code}")
    User findByCode(String code);

    /**
     * 修改指定用户激活状态
     *
     * @param user
     */
    @Update("update tab_user set status = 'Y' where uid=#{uid}")
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     *
     * @param username
     * @param password
     * @return
     */
    @Select("select * from tab_user where username = #{username} and password = #{password}")
    User findByUsernameAndPassword(String username, String password);

}
