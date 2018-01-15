package com.tylkowski.gamecms.controller;

import com.tylkowski.gamecms.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/pagination")
public class PaginationController {

@Autowired
CategoryService categoryPaginationRepository;

    @RequestMapping(value = {"/{page}", ""})
    public String showAllGames(@PathVariable Optional<Integer> page, Model model) {
        model.addAttribute("tekst", "a");
        if (page.isPresent()) {
            System.out.println("isPresent(wystepuje)");
            return "categoryPagination";
        } else {
            System.out.println("notPresent(nie wystepuje)");
            return "categoryPagination";
        }
    }


}

