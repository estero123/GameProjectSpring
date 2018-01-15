package com.tylkowski.gamecms.controller;

import com.tylkowski.gamecms.entity.Game;
import com.tylkowski.gamecms.service.CategoryService;
import com.tylkowski.gamecms.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/s")
public class SearchController extends MainController{
    @Autowired
    GameService gameService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = {"/{gameName}/{pageNumber}","/{gameName}"})
    public String searchByGameName(@PathVariable String gameName, @PathVariable Optional<Integer> pageNumber , Model model) {

        if (!pageNumber.isPresent() || pageNumber.get()<0) pageNumber = Optional.of(0);

        Page<Game> gameList;


        gameList = getGames(gameName, pageNumber.get());

        model.addAttribute("gameName", gameName);
        model.addAttribute("gamesList", gameList);
        model.addAttribute("gameListTotalPages", gameList.getTotalPages());
        model.addAttribute("actualPage", gameList.getNumber());
        model.addAttribute("categories", categoryService.findAll());
        return "searchresult";
    }

    private Page<Game> getGames(String gameName, int pageNumber) {
        PageRequest pageRequest = new PageRequest(pageNumber, 28);
        Page<Game> gameList = gameService.findAllByGameNameContainingAndVisibleOrderByMobileDescGameDateLastEditDesc(gameName, true, pageRequest);
        if (pageNumber>gameList.getTotalPages() || pageNumber < 0) gameList = getGames(gameName, 0);
        return gameList;
    }
}
