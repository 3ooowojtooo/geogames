package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.ObservableField;
import android.text.Editable;

import java.util.Objects;

public class GameDetailsCreationObservable {

    public static final String TYPE_PUBLIC = "PUBLIC";
    public static final String TYPE_PRIVATE = "PRIVATE";

    public ObservableField<String> title;
    public ObservableField<String> description;
    private String gameType = TYPE_PUBLIC;
    public GameDetailsCreationObservable(){
        description = new ObservableField<>("");
        title = new ObservableField<>("");
    }

    public void afterTitleChanged(Editable s){
        if(!Objects.equals(title.get(), s.toString()))
            title.set(s.toString());
    }

    public void afterDescriptionChanged(Editable s){
        if(!Objects.equals(description.get(), s.toString()))
            description.set(s.toString());
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType){
        this.gameType = gameType;
    }

    public GameDetailsCreation toNotObservable(){
        return new GameDetailsCreation(title.get(), description.get(), gameType);
    }

}
