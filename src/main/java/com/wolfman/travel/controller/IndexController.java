package com.wolfman.travel.controller;

import com.wolfman.travel.bean.Route;
import com.wolfman.travel.service.impl.RouteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    RouteServiceImpl routeService;


    /**
     * 查询首页数据
     *
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/user/findAllRoutes")
    public List<Route> findAllRoutes(Model model) {
        return routeService.findAllRoutes();
    }

    /**
     * 跳转到index页面
     * @param model
     * @return
     */
    @GetMapping("/user/index")
    public String index(Model model) {
        return "index";
    }


}
