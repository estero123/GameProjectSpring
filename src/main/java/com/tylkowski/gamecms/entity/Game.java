package com.tylkowski.gamecms.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameName;
    @Column(length = 1500)
    private String gameDescription;

    private String gameSlogan;

    private String gameUrl;

    private String gameThumbnail;

    private Date gameDateLastEdit;

    private int played;

    private boolean visible;

    private boolean mobile;

    @ManyToMany( fetch = FetchType.LAZY)
    @JoinTable(name = "CATEGORIES_GAMES", joinColumns = {@JoinColumn(name = "GAME_ID")},inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID")})
    private Set<Category> gameCategories;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Set<Category> getGameCategories() {
        return gameCategories;
    }

    public void setGameCategories(Set<Category> gameCategories) {
        this.gameCategories = gameCategories;
    }

    public String getGameDescription() {
        return gameDescription;
    }

    public void setGameDescription(String gameDescription) {
        this.gameDescription = gameDescription;
    }

    public String getGameSlogan() {
        return gameSlogan;
    }

    public void setGameSlogan() {
        gameSlogan = getGameName().toLowerCase()
                .replace("-", "")
                .replace(":", "")
                .replace(" ", "-")
                .replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś", "s")
                .replace("ź", "z")
                .replace("ż", "z")
                .replace("!", "")
                .replace("/", "")
                .replace("\"", "")
                .replace("?", "").trim();
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getGameThumbnail() {
        return gameThumbnail;
    }

    public void setGameThumbnail(String gameThumbnail) {
        this.gameThumbnail = gameThumbnail;
    }

    public Date getGameDateLastEdit() {
        return gameDateLastEdit;
    }

    public void setGameDateLastEdit() throws ParseException {
        Date date = new Date();
        SimpleDateFormat spl=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d = spl.format(date);
        date = spl.parse(d);
        gameDateLastEdit = date;
    }

    public int getPlayed() {
        return played;
    }

    public void setPlayed(int played) {
        this.played = played;
    }

    public boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean getMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Game game = (Game) o;

        return getGameName().equals(game.getGameName());
    }

    @Override
    public int hashCode() {
        return getGameName().hashCode();
    }
}
