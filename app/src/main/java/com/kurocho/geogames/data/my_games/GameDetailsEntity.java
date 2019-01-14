package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "game_details",
    indices = {@Index(value = {"gameId"}, unique = true)})
public class GameDetailsEntity {

    @PrimaryKey
    private int gameId;

    private String title;
    private String description;
    private String gameType;
    private String inviteUrl;
    private int levelsCompleted;
    private int numbersOfLevels;
    private long dateCreated;

    public static GameDetailsEntity fromGameDetails(GameDetails gameDetails, int levelsCompleted, int numberOfLevels){
        GameDetailsEntity entity = new GameDetailsEntity();
        entity.setGameId(gameDetails.getGameId());
        entity.setLevelsCompleted(levelsCompleted);
        entity.setNumbersOfLevels(numberOfLevels);
        entity.setTitle(gameDetails.getTitle());
        entity.setInviteUrl(gameDetails.getInviteUrl());
        entity.setGameType(gameDetails.getGameType());
        entity.setDescription(gameDetails.getDescription());
        entity.setDateCreated(gameDetails.getDateCreated());
        return entity;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public void setInviteUrl(String inviteUrl) {
        this.inviteUrl = inviteUrl;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    public void setLevelsCompleted(int levelsCompleted) {
        this.levelsCompleted = levelsCompleted;
    }

    public int getNumbersOfLevels() {
        return numbersOfLevels;
    }

    public void setNumbersOfLevels(int numbersOfLevels) {
        this.numbersOfLevels = numbersOfLevels;
    }
}
