package com.kurocho.geogames.viewmodels.create_game;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CreateGameViewModel extends ViewModel {

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private List<GameLevelCreationObservable> gameLevelCreationObservableList;

    @Inject
    CreateGameViewModel(){
        gameDetailsCreationObservable = new GameDetailsCreationObservable();
        gameLevelCreationObservableList = new ArrayList<>();

        GameLevelCreationObservable ob = new GameLevelCreationObservable();
        ob.ord.set("1");
        gameLevelCreationObservableList.add(ob);
    }

    public GameDetailsCreationObservable getGameDetailsCreationObservable(){
        return gameDetailsCreationObservable;
    }

    public List<GameLevelCreationObservable> getGameLevelCreationObservableList(){
        return gameLevelCreationObservableList;
    }

}
