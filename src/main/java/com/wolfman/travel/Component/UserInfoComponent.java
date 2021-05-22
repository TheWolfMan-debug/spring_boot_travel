package com.wolfman.travel.Component;

import com.wolfman.travel.bean.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public final class UserInfoComponent {


    /**
     * 在客户端保存用户的信息
     *
     * @param response
     * @param u
     */
    public void saveUserInCookie(HttpServletResponse response, User u) {
        Cookie cookie = new Cookie("userUid", String.valueOf(u.getUid()));
        //设置cookie的存活时间 一个小时
        cookie.setMaxAge(60 * 60);
        //设置路径
        cookie.setPath("/");
        //添加cookie
        response.addCookie(cookie);
    }

    /**
     * 在服务器端保存用户信息
     *
     * @param session
     * @param u
     */
    public void saveUserInSession(HttpSession session, User u) {
        //获取onlineUser
        User onlineUser = (User) session.getAttribute("onlineUser");
        if (onlineUser == null) {
            //如果session中没有onlineUser，则创建并添加
            session.setAttribute("onlineUser", u);
            //设置保存时长为1小时
            session.setMaxInactiveInterval(60 * 60);
        }
    }


    /**
     * 销毁cookie和session
     *
     * @param request
     * @param response
     */
    public void destroyCookieAndSession(HttpServletRequest request, HttpServletResponse response) {
        //获取session
        HttpSession session = request.getSession();
        //获取所有Cookie
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            //如果用户之前已经勾选了记住我
            if ("userUid".equals(cookie.getName())) {
                //获取用户信息
                String uid = cookie.getValue();
                //销毁session中该用户的信息
                destroySession(uid, session);
                //销毁cookie中的用户信息
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
        }
    }


    /**
     * 销毁session
     *
     * @param uid
     * @param session
     */
    private void destroySession(String uid, HttpSession session) {

        User user = (User) session.getAttribute("onlineUser");
        if (uid.equals(String.valueOf(user.getUid()))) {
            //销毁session
            session.invalidate();
        }

    }


    /**
     * 获取当前在线用户
     *
     * @param request
     * @return
     */
    public User findOneUser(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                //如果session存在该用户，返回
                if ("userUid".equals(cookie.getName())) {
                    //获取用户信息
                    String uid = cookie.getValue();
                    User user = (User) session.getAttribute("onlineUser");
                    //如果用户不为空
                    if (user != null && String.valueOf(user.getUid()).equals(uid)) {
                        return user;
                    }
                }
            }
        }
        return null;
    }


}


