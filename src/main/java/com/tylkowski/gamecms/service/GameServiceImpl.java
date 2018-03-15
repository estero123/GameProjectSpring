package com.tylkowski.gamecms.service;

import com.tylkowski.gamecms.entity.Game;
import com.tylkowski.gamecms.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class GameServiceImpl implements GameService {
    @Autowired
    GameRepository gameRepository;

    @Override
    public List<Game> findTop20ByVisibleOrderByPlayedDesc(Boolean visible) {
        return gameRepository.findTop20ByVisibleOrderByPlayedDesc(visible);
    }

    @Override
    public List<Game> findTop20ByVisibleOrderByMobileDescPlayedDesc(Boolean visible) {
        return gameRepository.findTop20ByVisibleOrderByMobileDescPlayedDesc(visible);
    }

    @Override
    public Page<Game> findAllByGameNameContainingAndVisibleOrderByMobileDescGameDateLastEditDesc(String gameName, boolean visible, Pageable pageable) {
        return gameRepository.findAllByGameNameContainingAndVisibleOrderByMobileDescGameDateLastEditDesc(gameName, visible, pageable);
    }

    @Override
    public Page<Game> findAllByGameCategoriesOrderByPlayedDesc(Long id, boolean visible, Pageable pageable) {
        return gameRepository.findAllByGameCategoriesOrderByPlayedDesc(id, visible,pageable);
    }

    @Override
    public Page<Game> findAllMobileByGameCategoriesOrderByPlayedDesc(Long id,  boolean visible, Pageable pageable) {
        return gameRepository.findAllMobileByGameCategoriesOrderByPlayedDesc(id, visible, pageable);
    }

    @Override
    public Set<Game> findRandomGamesByCategory(Long id, int howManyGames, boolean visible) {
        return gameRepository.findRandomGamesByCategory(id,howManyGames,visible);
    }

    @Override
    public Set<Game> findRandomMobileGamesByCategory(Long id, int howManyGames, boolean visible) {
        return gameRepository.findRandomMobileGamesByCategory(id,howManyGames, visible);
    }

    @Override
    public Game findByGameSloganAndVisible(String gameSlogan, Boolean visible) {
        return gameRepository.findByGameSloganAndVisible(gameSlogan,visible);
    }

    @Override
    public Iterable<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public void save(Game game) {
        gameRepository.save(game);
    }

    @Override
    public Game findOne(Long gameId) {
        return gameRepository.findOne(gameId);
    }

    @Override
    public void delete(Game game) {
        gameRepository.delete(game);
    }

    @Override
    public void delete(Long gameId) {
        gameRepository.delete(gameId);
    }
}
