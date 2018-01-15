package com.tylkowski.gamecms.repository;

import com.tylkowski.gamecms.entity.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Set;

public interface GameRepository extends PagingAndSortingRepository<Game, Long> {
    List<Game> findTop20ByVisibleOrderByPlayedDesc(Boolean visible);
    List<Game> findTop20ByVisibleOrderByMobileDescPlayedDesc(Boolean visible);

    Page<Game> findAllByGameNameContainingAndVisibleOrderByMobileDescGameDateLastEditDesc(String gameName, boolean visible, Pageable pageable);

    @Query(value = "SELECT id,game_description, game_name, game_slogan, game_thumbnail, mobile, played, visible\n" +
            "FROM game inner join categories_games\n" +
            "on categories_games.game_id = game.id\n" +
            "where categories_games.category_id = ?1 AND visible=?2 order by played desc \n#pageable\n",
            countQuery = "SELECT count(*)" +
                    "FROM game inner join categories_games\n" +
                    "on categories_games.game_id = game.id\n" +
                    "where categories_games.category_id = ?1 AND visible=?2",
            nativeQuery = true)
    Page<Game> findAllByGameCategoriesOrderByPlayedDesc(Long id, boolean visible, Pageable pageable);

    @Query(value = "SELECT id, game_description, game_name, game_slogan, game_thumbnail, mobile, played, visible\n" +
            "FROM game inner join categories_games\n" +
            "on categories_games.game_id = game.id\n" +
            "where categories_games.category_id = ?1 AND visible=?2 order by mobile desc, played desc \n#pageable\n",
            countQuery = "SELECT count(*)" +
                    "FROM game inner join categories_games\n" +
                    "on categories_games.game_id = game.id\n" +
                    "where categories_games.category_id = ?1 AND visible=?2",
            nativeQuery = true)
    Page<Game> findAllMobileByGameCategoriesOrderByPlayedDesc(Long id, boolean visible, Pageable pageable);

    @Query(value = "SELECT *\n" +
            "FROM game inner join categories_games\n" +
            "on categories_games.game_id = game.id\n" +
            "where categories_games.category_id = ?1 AND visible=?3 order by RAND() LIMIT 0,?2", nativeQuery = true)
    Set<Game> findRandomGamesByCategory(Long id, int howManyGames, boolean visible);

    @Query(value = "SELECT *\n" +
            "FROM game inner join categories_games\n" +
            "on categories_games.game_id = game.id\n" +
            "where categories_games.category_id = ?1 AND visible=?3 AND mobile=TRUE order by RAND() LIMIT 0,?2", nativeQuery = true)
    Set<Game> findRandomMobileGamesByCategory(Long id, int howManyGames, boolean visible);


    Game findByGameSloganAndVisible(String gameSlogan, Boolean visible);
}
