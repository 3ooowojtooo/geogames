package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.ObservableField;

public class GameDetailsCreationObservable {

    public static final String TYPE_PUBLIC = "PUBLIC";
    public static final String TYPE_PRIVATE = "PRIVATE";

    public ObservableField<String> description;
    private String gameType = TYPE_PUBLIC;
    public ObservableField<String> title;

    public GameDetailsCreationObservable(){
        description = new ObservableField<>("");
        title = new ObservableField<>("");
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType){
        this.gameType = gameType;
    }
}
