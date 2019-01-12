package com.kurocho.geogames.viewmodels.create_game;

import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.utils.create_game.CreateGameUtils;

import javax.inject.Inject;
import java.util.ArrayList;

public class CreateGameViewModel extends ViewModel {

    private CreateGameUtils createGameUtils;

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private ListOfGameLevelCreationObservables gameLevelCreationObservableList;

    @Inject
    CreateGameViewModel(CreateGameUtils createGameUtils){
        this.createGameUtils = createGameUtils;
        gameDetailsCreationObservable = new GameDetailsCreationObservable();
        gameLevelCreationObservableList = new ListOfGameLevelCreationObservables();
    }

    public GameDetailsCreationObservable getGameDetailsCreationObservable(){
        return gameDetailsCreationObservable;
    }

    public ListOfGameLevelCreationObservables getGameLevelCreationObservablesList(){
        return gameLevelCreationObservableList;
    }

    public void createGame(){
        GameDetailsCreation gameDetails = gameDetailsCreationObservable.toNotObservable();
        ArrayList<GameLevelCreation> levelsDetails = gameLevelCreationObservableList.toNotObservable();


    }

}
