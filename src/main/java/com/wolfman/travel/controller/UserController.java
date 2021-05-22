package com.wolfman.travel.controller;

import com.wolfman.travel.Component.*;
import com.wolfman.travel.bean.Route;
import com.wolfman.travel.bean.User;
import com.wolfman.travel.service.impl.FavoriteServiceImpl;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import com.wolfman.travel.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;


@Controller
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    RouteServiceImpl routeService;

    @Autowired
    FavoriteServiceImpl favoriteService;

    @Autowired
    RememberMeComponent rememberMeComponent;

    @Autowired
    Md5Component md5Component;

    @Autowired
    LoginComponent loginComponent;

    @Autowired
    CheckCodeComponent checkCodeComponent;

    @Autowired
    UserInfoComponent userInfoComponent;

    /**
     * 跳转到routeList页面
     *
     * @return
     */
    @GetMapping("/user/routeList")
    public String routeList() {
        return "routeList";
    }


    /**
     * 跳转到routeDetail页面
     *
     * @return
     */
    @GetMapping("/user/routeDetail")
    public String routeDetail() {
        return "routeDetail";
    }

    /**
     * 根据rid查询路线详情
     *
     * @param rid
     * @return
     */
    @ResponseBody
    @GetMapping("/user/routeDetailFindOne")
    public Route routeFindOne(int rid) {
        Route routeDetail = routeService.findOne(rid);
        return routeDetail;
    }


    /**
     * 跳转到login页面
     *
     * @return
     */
    @GetMapping("/user/login")
    public String login(HttpServletRequest request, Model model) {
        rememberMeComponent.rememberModel(request, model);
        return "login";
    }


    /**
     * 用户登录功能
     *
     * @param user
     * @param session
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public Map userLogin(User user, String rememberMeCheck, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        //调用Service查询用户信息
        User u = userService.login(user);
        //检查用户登录信息，并返回结果信息
        Map map = loginComponent.loginCheck(u, session, response, request);
        //调用组件 记住用户
        rememberMeComponent.rememberMe(rememberMeCheck, user, request, response);
        return map;
    }

    /**
     * 退出功能
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/user/exit")
    public String exit(HttpServletRequest request, HttpServletResponse response) {
        //销毁session和cookie中当前用户的信息数据
        userInfoComponent.destroyCookieAndSession(request, response);
        return "redirect:login";
    }

    /**
     * 跳转到register页面
     *
     * @return
     */
    @GetMapping("/user/register")
    public String stepToUserRegister() {
        return "register";
    }

    /**
     * 用户登录检查
     *
     * @param user
     * @param response
     * @param request
     * @return
     */
    @PostMapping("/user/register")
    @ResponseBody
    public Map userRegister(User user, HttpServletResponse response, HttpServletRequest request) {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        //确认验证码
        if (!checkCodeComponent.checkCode(request)) {
            map.put("errorMsg", "验证码错误");
            map.put("flag", false);
            return map;
        }
        //调用service完成注册
        boolean flag = userService.register(user, request);
        //响应结果
        if (flag) {
            //注册成功
            map.put("flag", true);
        } else {
            //注册失败
            map.put("errorMsg", "注册失败！");
            map.put("flag", false);
        }
        return map;
    }

    /**
     * 用户激活功能
     *
     * @param code
     * @param request
     * @param response
     */
    @GetMapping("/user/active")
    public void userActive(String code, HttpServletRequest request, HttpServletResponse response) {
        if (code != null) {
            //调用service完成激活
            boolean flag = userService.active(code);
            //判断标记
            String msg;
            if (flag) {
                //激活成功
                String contextPath = request.getContextPath();
                msg = "激活成功，请<a href=\"" + contextPath + "/user/login\">登录</a>";

            } else {
                //激活失败
                msg = "激活失败，请联系管理员!";
            }
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 跳转到registerOk页面
     *
     * @param user
     * @return
     */
    @GetMapping("/user/registerOk")
    public String registerOk(User user) {
        return "registerOk";
    }

    /**
     * 判断当前用户是否登录
     * @param request
     * @return
     */
    @GetMapping("/user/findOne")
    @ResponseBody
    public User findOne(HttpServletRequest request) {
        User onlineUser = userInfoComponent.findOneUser(request);
        return onlineUser;
    }


}
