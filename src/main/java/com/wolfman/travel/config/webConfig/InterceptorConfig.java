package com.wolfman.travel.config.webConfig;

import com.wolfman.travel.Component.UserInfoComponent;
import com.wolfman.travel.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 拦截器配置，登陆检查
 */
public class InterceptorConfig implements HandlerInterceptor {

    @Autowired
    UserInfoComponent userInfoComponent;

    /**
     * 目标方法执行之前执行
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                //如果session存在该用户，返回
                if ("userUid".equals(cookie.getName())) {
                    //获取用户信息
                    String uid = cookie.getValue();
                    User user = (User) session.getAttribute("onlineUser");
                    if (user != null && String.valueOf(user.getUid()).equals(uid)) {
                        return true;
                    }
                }
            }
        }
        // 未登陆，返回登陆页面
        request.setAttribute("errorMsg", "没有权限请先登陆");
        //转发到登录页面
        request.getRequestDispatcher("/user/login").forward(request, response);
        return false;

    }


}
