package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.ObservableField;

public class GameLevelCreationObservable {

    public ObservableField<String> answer;
    public ObservableField<String> description;
    public ObservableField<String> ord;

    public GameLevelCreationObservable(){
        answer = new ObservableField<>("");
        description = new ObservableField<>("");
        ord = new ObservableField<>("");
    }

}
