package com.tylkowski.gamecms.service;

import com.tylkowski.gamecms.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface GameService {
    List<Game> findTop20ByVisibleOrderByPlayedDesc(Boolean visible);
    List<Game> findTop20ByVisibleOrderByMobileDescPlayedDesc(Boolean visible);
    Page<Game> findAllByGameNameContainingAndVisibleOrderByMobileDescGameDateLastEditDesc(String gameName, boolean visible, Pageable pageable);
    Page<Game> findAllByGameCategoriesOrderByPlayedDesc(Long id,  boolean visible, Pageable pageable);
    Page<Game> findAllMobileByGameCategoriesOrderByPlayedDesc(Long id, boolean visible, Pageable pageable);
    Set<Game> findRandomGamesByCategory(Long id, int howManyGames, boolean visible);
    Set<Game> findRandomMobileGamesByCategory(Long id, int howManyGames, boolean visible);
    Game findByGameSloganAndVisible(String gameSlogan, Boolean visible);
    Iterable<Game> findAll();
    void save(Game game);
    Game findOne(Long gameId);
    void delete(Game game);
    void delete(Long gameId);
}
