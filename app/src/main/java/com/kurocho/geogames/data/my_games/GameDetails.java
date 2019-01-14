package com.kurocho.geogames.data.my_games;

import com.kurocho.geogames.data.Timestamp;

public class GameDetails {
    private int gameId;
    private String title;
    private String description;
    private String gameType;
    private String inviteUrl;
    private long dateCreated;

    public GameDetails(int gameId, String title, String description, String gameType, String inviteUrl, long dateCreated) {
        this.gameId = gameId;
        this.title = title;
        this.description = description;
        this.gameType = gameType;
        this.inviteUrl = inviteUrl;
        this.dateCreated = dateCreated;
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
}
