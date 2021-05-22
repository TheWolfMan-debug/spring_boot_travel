package com.wolfman.travel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FavoriteRankController {

    /**
     * 跳转到favoriteRank界面
     *
     * @return
     */
    @GetMapping("/user/favoriteRank")
    public String favoriteRank() {
        return "favoriteRank";
    }

}
