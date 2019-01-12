package com.kurocho.geogames.viewmodels.create_game;

import android.arch.lifecycle.ViewModel;
import android.util.Log;

import javax.inject.Inject;
import java.util.ArrayList;

public class CreateGameViewModel extends ViewModel {

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private ListOfGameLevelCreationObservables gameLevelCreationObservableList;

    @Inject
    CreateGameViewModel(){
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

        Log.i("CREATE", "T: " + gameDetails.getDescription());
    }

}
