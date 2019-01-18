package com.kurocho.geogames.data.search;

import android.arch.persistence.room.Ignore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SearchGameDetails {

    private long dateCreated;
    private String description;
    private int gameId;
    private String gameType;
    private String inviteUrl;
    private String title;

    public SearchGameDetails(long dateCreated, String description, int gameId, String gameType, String inviteUrl, String title) {
        this.dateCreated = dateCreated;
        this.description = description;
        this.gameId = gameId;
        this.gameType = gameType;
        this.inviteUrl = inviteUrl;
        this.title = title;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameType() {
        return gameType;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getFormattedCreationData(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        Date date = new Date(dateCreated);
        return dateFormat.format(date);
    }

}
