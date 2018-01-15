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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/gry")
public class CategoryController extends MainController{
    private final static int PAGE_SIZE = 40;

    @Autowired
    CategoryService categoryService;

    @Autowired
    GameService gameService;

    @RequestMapping(value = {"/{categorySlogan}/{pageNumber}", "/{categorySlogan}"})
    public String showByCategory(@PathVariable String categorySlogan, @PathVariable Optional<Integer> pageNumber, Model model, @ModelAttribute("device") String device ){
        Category category = categoryService.findByCategorySlogan(categorySlogan);

        if (!pageNumber.isPresent() || pageNumber.get()<0) pageNumber = Optional.of(0);
        Page<Game> gameList = getGames(pageNumber.get(), device, category);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", category);
        model.addAttribute("gameListTotalPages", gameList.getTotalPages());
        model.addAttribute("actualPage", gameList.getNumber());
        model.addAttribute("gameList", gameList);
        return "category";
    }

    private Page<Game> getGames(int pageNumber,String device, Category category) {
        Page<Game> gameList;
        PageRequest pageRequest = new PageRequest(pageNumber, PAGE_SIZE);
        if (device.equals("mobile")) {
            gameList = gameService.findAllMobileByGameCategoriesOrderByPlayedDesc(category.getId(),  true, pageRequest);
        } else {
            gameList = gameService.findAllByGameCategoriesOrderByPlayedDesc(category.getId(),  true, pageRequest);
        }
        if (pageNumber>gameList.getTotalPages() || pageNumber < 0) gameList = getGames(0, device, category);
        return gameList;
    }

}
