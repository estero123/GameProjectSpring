package com.tylkowski.gamecms.controller;

import com.tylkowski.gamecms.entity.Category;
import com.tylkowski.gamecms.entity.Game;
import com.tylkowski.gamecms.service.CategoryService;
import com.tylkowski.gamecms.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/g")
public class GameController extends MainController{
    @Autowired
    GameService gameService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping("/{gameSlogan}")
    public String showGame(@PathVariable String gameSlogan, Model model, @ModelAttribute("device") String device ) {

        Game game = gameService.findByGameSloganAndVisible(gameSlogan, true);

        Map<String, Set<Game>> gamesByCategories = new HashMap<>();
        if (device.equals("mobile")) {
            for (Category category : game.getGameCategories()) {

                gamesByCategories.put(category.getCategoryName(), gameService.findRandomMobileGamesByCategory(category.getId(), 8, true));
            }
        } else {
            for (Category category : game.getGameCategories()) {

                gamesByCategories.put(category.getCategoryName(), gameService.findRandomGamesByCategory(category.getId(), 8, true));
            }
        }
        game.setPlayed(game.getPlayed() + 1);
        gameService.save(game);
        model.addAttribute("game", game);
        model.addAttribute("gamesByCategories", gamesByCategories);
        model.addAttribute("categories", categoryService.findAll());
        return "game";
    }
}
