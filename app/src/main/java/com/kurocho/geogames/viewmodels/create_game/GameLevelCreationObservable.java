package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.ObservableField;

public class GameLevelCreationObservable {

    public ObservableField<String> answer;
    public ObservableField<String> description;
    public ObservableField<String> ord;

    GameLevelCreationObservable(int ord){
        answer = new ObservableField<>("");
        description = new ObservableField<>("");
        this.ord = new ObservableField<>(String.valueOf(ord));
    }

    void setOrdInteger(int ord){
        this.ord.set(String.valueOf(ord));
    }

    GameLevelCreation toNotObservable(){
        return new GameLevelCreation(description.get(), answer.get(), ord.get());
    }

}
