package com.tylkowski.gamecms.controller;

import com.tylkowski.gamecms.entity.Category;
import com.tylkowski.gamecms.entity.Game;
import com.tylkowski.gamecms.service.CategoryService;
import com.tylkowski.gamecms.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/admin")
@Secured("ROLE_ADMIN")
public class AdminController extends MainController{
    @Autowired
    GameService gameService;
    @Autowired
    CategoryService categoryService;

    @RequestMapping("/")
    public String showGamesHomepage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("gamesList", gameService.findAll());
        model.addAttribute("userRoles", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
        return "games";
    }


    @RequestMapping(value = "/savegame", method = RequestMethod.POST)
    @ResponseBody
    public String saveGame(@RequestParam String gameName, @RequestParam Boolean visible, @RequestParam Boolean mobile ,@RequestParam String gameDescription, @RequestParam String gameUrl, @RequestParam String gameThumbnail, @RequestParam(value = "categoryIds[]") Long[] categoryIds) throws ParseException {
        Game game = new Game();
        game.setGameName(gameName);
        game.setGameDescription(gameDescription);
        game.setGameUrl(gameUrl);
        game.setGameThumbnail(gameThumbnail);
        game.setGameSlogan();
        game.setVisible(visible);
        game.setMobile(mobile);
        game.setGameDateLastEdit();
        Set<Category> categorySet = new HashSet<>();
        for (Long categoryId : categoryIds) {
            categorySet.add(categoryService.findOne(categoryId));
        }
        game.setGameCategories(categorySet);
        gameService.save(game);
        return game.getId().toString();
    }


    @RequestMapping(value = "/updategame/{Id}")
    public String updateGame(@PathVariable Long Id, Model model) {
        model.addAttribute("game", gameService.findOne(Id));
        model.addAttribute("gamesList", gameService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "game-update";
    }

    @RequestMapping(value = "/changegamevisibility", method = RequestMethod.POST)
    @ResponseBody
    public String changegamevisibility(@RequestParam Long Id) {
        Game game = gameService.findOne(Id);
        game.setVisible(!game.getVisible());
        gameService.save(game);
        return Id.toString();
    }


    @RequestMapping(value = "/updatesavedgame", method = RequestMethod.POST)
    @ResponseBody
    public String updateSavedGame(@RequestParam Long Id, @RequestParam String gameName, @RequestParam String gameDescription, @RequestParam String gameUrl, @RequestParam String gameThumbnail, @RequestParam Boolean visible, @RequestParam Boolean mobile ,@RequestParam(value = "categoryIds[]") Long[] categoryIds) throws ParseException {
        Game game = gameService.findOne(Id);
        game.setId(Id);
        game.setGameName(gameName);
        game.setGameDescription(gameDescription);
        game.setGameUrl(gameUrl);
        game.setGameThumbnail(gameThumbnail);
        game.setVisible(visible);
        game.setMobile(mobile);
        game.setGameSlogan();
        game.setGameDateLastEdit();
        Set<Category> categorySet = new HashSet<>();
        for (Long categoryId : categoryIds) {
            categorySet.add(categoryService.findOne(categoryId));
        }
        game.setGameCategories(categorySet);
        gameService.save(game);
        return game.getId().toString();
    }



    @RequestMapping(value = "/removegame", method = RequestMethod.POST)
    @ResponseBody
    public String removeGame(@RequestParam Long Id){
        gameService.delete(Id);
        return Id.toString();
    }

                    //      GAME SECTION
                    //=============================
                    //    CATEGORY SECTION

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public String categoriesList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories";
    }

    @RequestMapping(value = "/updatecategory/{Id}")
    public String updateCategory(@PathVariable Long Id, Model model) {
        model.addAttribute("category", categoryService.findOne(Id));
        model.addAttribute("categories", categoryService.findAll());
        return "category-update";
    }

    @RequestMapping(value = "/savecategory", method = RequestMethod.POST)
    @ResponseBody
    public String saveCategory(@RequestBody Category category) {
        categoryService.save(category);
        return category.getId().toString();
    }

    @RequestMapping(value = "/removecategory", method = RequestMethod.POST)
    @ResponseBody
    public String removeCategory(@RequestParam Long Id) {
        Category category = categoryService.findOne(Id);
        for (Game game : category.getGames()) {
            game.getGameCategories().remove(category);
            gameService.save(game);
        }
        categoryService.delete(category);
        return Id.toString();
    }
    @ModelAttribute("userRoles")
    public void getUserRoles(HttpServletRequest req) {
        req.getSession().setAttribute("userRoles", String.valueOf(SecurityContextHolder.getContext().getAuthentication().getAuthorities()));
    }
}
