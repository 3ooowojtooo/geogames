package com.kurocho.geogames.viewmodels.create_game;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.utils.create_game.CreateGameUtils;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataWrapper;

import javax.inject.Inject;
import java.util.ArrayList;

public class CreateGameViewModel extends ViewModel {

    private CreateGameUtils createGameUtils;

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private ListOfGameLevelCreationObservables gameLevelCreationObservableList;

    private MutableLiveData<CreateGameLiveDataWrapper> createGameLiveData;

    @Inject
    CreateGameViewModel(CreateGameUtils createGameUtils){
        this.createGameUtils = createGameUtils;
        gameDetailsCreationObservable = new GameDetailsCreationObservable();
        gameLevelCreationObservableList = new ListOfGameLevelCreationObservables();

        gameLevelCreationObservableList.createAndAppendNewGameLevelObject();

        createGameLiveData = new MutableLiveData<>();
        setIdleLiveDataStatus();
    }

    public GameDetailsCreationObservable getGameDetailsCreationObservable(){
        return gameDetailsCreationObservable;
    }

    public ListOfGameLevelCreationObservables getGameLevelCreationObservablesList(){
        return gameLevelCreationObservableList;
    }

    public LiveData<CreateGameLiveDataWrapper> getCreateGameLiveData(){
        return createGameLiveData;
    }

    public void createGame(){
        if(createGameLiveData.getValue() != null){
            if(!createGameLiveData.getValue().isInProgress()){
                setInProgressLiveDataStatus();

                GameDetailsCreation gameDetailsCreation = gameDetailsCreationObservable.toNotObservable();
                ArrayList<GameLevelCreation> gameLevelCreations = gameLevelCreationObservableList.toNotObservable();

                createGameUtils.createGame(gameDetailsCreation, gameLevelCreations, new CreateGameUtils.CreateGameCallback() {
                    @Override
                    public void onSuccess(String message) {
                        setSuccessLiveDataStatus(message);
                    }

                    @Override
                    public void onError(String message) {
                        setErrorLiveDataStatus(message);
                    }
                });
            }
        }
    }

    private void setIdleLiveDataStatus(){
        createGameLiveData.setValue(CreateGameLiveDataWrapper.idle());
    }

    private void setInProgressLiveDataStatus(){
        createGameLiveData.setValue(CreateGameLiveDataWrapper.inProgress());
    }

    private void setSuccessLiveDataStatus(String message){
        createGameLiveData.setValue(CreateGameLiveDataWrapper.success(message));
    }

    private void setErrorLiveDataStatus(String message){
        createGameLiveData.setValue(CreateGameLiveDataWrapper.error(message));
    }

}
