package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.ObservableField;
import android.text.Editable;

import java.util.Objects;

public class GameLevelCreationObservable {

    public ObservableField<String> answer;
    public ObservableField<String> description;
    public ObservableField<String> ord;

    GameLevelCreationObservable(int ord){
        answer = new ObservableField<>("");
        description = new ObservableField<>("");
        this.ord = new ObservableField<>(String.valueOf(ord));
    }

    public void afterDescriptionChanged(Editable s){
        if(!Objects.equals(description.get(), s.toString()))
            description.set(s.toString());
    }

    public void afterAnswerChanged(Editable s){
        if(!Objects.equals(answer.get(), s.toString()))
            answer.set(s.toString());
    }

    void setOrdInteger(int ord){
        this.ord.set(String.valueOf(ord));
    }

    GameLevelCreation toNotObservable(){
        return new GameLevelCreation(description.get(), answer.get(), ord.get());
    }

}
