package com.tylkowski.gamecms.controller;

import com.tylkowski.gamecms.entity.Category;
import com.tylkowski.gamecms.entity.Game;
import com.tylkowski.gamecms.service.CategoryService;
import com.tylkowski.gamecms.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class HomepageController extends MainController{

    @Autowired
    GameService gameService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/")
    public String showHomepage(Model model, @ModelAttribute("device") String device ) {
        Map<String, Page<Game>> gamesByCategories = new HashMap<>();
        if (device.equals("mobile")) {
            for (Category category : categoryService.findAll()) {
                gamesByCategories.put(category.getCategoryName(), gameService.findAllMobileByGameCategoriesOrderByPlayedDesc(category.getId(), true, new PageRequest(0, 20)));
            }
        } else {
            for (Category category : categoryService.findAll()) {
                gamesByCategories.put(category.getCategoryName(), gameService.findAllByGameCategoriesOrderByPlayedDesc(category.getId(), true, new PageRequest(0, 20)));
            }
        }

        List<Game> gamesListMostPlayed;
        if (device.equals("mobile")) {
            gamesListMostPlayed = gameService.findTop20ByVisibleOrderByMobileDescPlayedDesc(true);
        } else {
            gamesListMostPlayed = gameService.findTop20ByVisibleOrderByPlayedDesc(true);
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("gamesByCategories", gamesByCategories);
        model.addAttribute("gamesListMostPlayed", gamesListMostPlayed);
        return "homepage";
    }

    @RequestMapping("/login")
    public String showLoginForm() {
        return "login";
    }




}
