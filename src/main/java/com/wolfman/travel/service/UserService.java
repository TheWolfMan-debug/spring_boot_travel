package com.wolfman.travel.service;

import com.wolfman.travel.bean.User;

import javax.servlet.http.HttpServletRequest;

public interface UserService {

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    boolean register(User user, HttpServletRequest request);


    /**
     * 用户激活
     *
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    User login(User user);

}
