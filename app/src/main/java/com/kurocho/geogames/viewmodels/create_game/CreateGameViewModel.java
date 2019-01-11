package com.kurocho.geogames.viewmodels.create_game;

import android.arch.lifecycle.ViewModel;

import javax.inject.Inject;

public class CreateGameViewModel extends ViewModel {

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private ListOfGameLevelCreationObservables gameLevelCreationObservableList;

    @Inject
    CreateGameViewModel(){
        gameDetailsCreationObservable = new GameDetailsCreationObservable();
        gameLevelCreationObservableList = new ListOfGameLevelCreationObservables();

        gameLevelCreationObservableList.createAndAppendNewGameLevelObject();
        gameLevelCreationObservableList.createAndAppendNewGameLevelObject();
        gameLevelCreationObservableList.createAndAppendNewGameLevelObject();
    }

    public GameDetailsCreationObservable getGameDetailsCreationObservable(){
        return gameDetailsCreationObservable;
    }

    public ListOfGameLevelCreationObservables getGameLevelCreationObservablesList(){
        return gameLevelCreationObservableList;
    }

}
