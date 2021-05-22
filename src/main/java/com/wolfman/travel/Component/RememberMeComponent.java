package com.wolfman.travel.Component;

import com.wolfman.travel.bean.User;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public final class RememberMeComponent {

    /**
     * 记住我功能
     *
     * @param rememberMeCheck
     * @param user
     * @param request
     * @param response
     */
    public void rememberMe(String rememberMeCheck, User user, HttpServletRequest request, HttpServletResponse response) {
        //如果用户勾选了记住我
        if ("on".equals(rememberMeCheck)) {
            //将用户返回
            Cookie cookie = new Cookie("rememberMe", user.getUsername() + "#" + user.getPassword());
            //设置cookie的存活时间 一个小时
            cookie.setMaxAge(60 * 60);
            //设置路径
            cookie.setPath("/");
            //添加cookie
            response.addCookie(cookie);
        } else {//如果用户没有勾选，则销毁cookie
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                //如果用户之前已经勾选了记住我
                if ("rememberMe".equals(cookie.getName())) {
                    //销毁cookie
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                }
            }
        }
    }

    /**
     * 返回model数据
     *
     * @param request
     * @param model
     */
    public void rememberModel(HttpServletRequest request, Model model) {
        //获取所有Cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //如果用户之前已经勾选了记住我
            if ("rememberMe".equals(cookie.getName())) {
                //获取用户信息
                String user = cookie.getValue();
                //将用户存入Model中
                model.addAttribute("user", user);
                //保存自动登录状态
                model.addAttribute("rememberMeCheck", "checked");
            }
        }
    }


}
