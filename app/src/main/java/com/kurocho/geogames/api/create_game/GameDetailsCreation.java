package com.kurocho.geogames.api.create_game;

public class GameDetailsCreation {

    private String title;
    private String description;
    private String gameType;

    public GameDetailsCreation(String title, String description, String gameType){
        this.title = title;
        this.description = description;
        this.gameType = gameType;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getGameType() {
        return gameType;
    }
}
