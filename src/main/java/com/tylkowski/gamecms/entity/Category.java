package com.tylkowski.gamecms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String categoryName;

    private String categorySlogan;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "gameCategories", cascade = {CascadeType.MERGE, CascadeType.PERSIST} )
    private Set<Game> games = new HashSet<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        setCategorySlogan();
    }

    public Set<Game> getGames() {
        return games;
    }

    public void setGames(Set<Game> games) {
        this.games = games;
    }
    public int getGamesSize() {
        return games.size();
    }

    public String getCategorySlogan() {
        return categorySlogan;
    }

    public void setCategorySlogan() {
        categorySlogan = getCategoryName().toLowerCase()
                .replace(" ", "-")
                .replace("ą", "a")
                .replace("ć", "c")
                .replace("ę", "e")
                .replace("ł", "l")
                .replace("ń", "n")
                .replace("ó", "o")
                .replace("ś","s")
                .replace("ź", "z")
                .replace("ż", "z");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Category category = (Category) o;

        return getCategoryName().equals(category.getCategoryName());
    }

    @Override
    public int hashCode() {
        return getCategoryName().hashCode();
    }
}
