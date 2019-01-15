package com.kurocho.geogames.viewmodels.play_game;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.utils.play_game.PlayGameUtils;

import javax.inject.Inject;

public class PlayGameViewModel extends ViewModel {

    private PlayGameUtils playGameUtils;

    private boolean isInitialized;
    private int gameId;
    private boolean lastLevel;

    private GameDetailsEntity gameDetailsEntity;
    private DecryptedLevelEntity currentLevel;
    private EncryptedLevelEntity nextLevel;

    private MutableLiveData<PlayGameLiveDataWrapper> playGameLiveData;

    @Inject
    PlayGameViewModel(PlayGameUtils playGameUtils){
        this.playGameUtils = playGameUtils;
        this.playGameLiveData = new MutableLiveData<>();
        clear();
    }

    public void clear(){
        isInitialized = false;
        lastLevel = false;
        gameDetailsEntity = null;
        currentLevel = null;
        nextLevel = null;
        setIdleLiveDataStatus();
    }

    public void initialize(int gameId){
        this.gameId = gameId;
        this.isInitialized = true;
        Log.i("PLAY", "vm-init: " + gameId);
        loadCurrentGameAndLevel();
    }

    public LiveData<PlayGameLiveDataWrapper> getPlayGameLiveData(){
        return playGameLiveData;
    }

    private void loadCurrentGameAndLevel(){
        if(playGameLiveData.getValue() != null) {
            if(!playGameLiveData.getValue().isInProgress()) {
                setInProgressLiveDataStatus();
                playGameUtils.getGameAndCurrentLevel(gameId, (game, nextLevel, decryptedLevel) -> {
                    Log.i("PLAY", "got game");
                    this.gameDetailsEntity = game;
                    this.currentLevel = decryptedLevel;
                    this.nextLevel = nextLevel;

                    if (nextLevel == null) {
                        lastLevel = true;
                    } else {
                        lastLevel = false;
                    }
                    setLoadedLiveDataStatus();
                });
            }
        }
    }

    private void setIdleLiveDataStatus(){
        playGameLiveData.setValue(PlayGameLiveDataWrapper.idle());
    }

    private void setInProgressLiveDataStatus(){
        playGameLiveData.setValue(PlayGameLiveDataWrapper.inProgress());
    }

    private void setLoadedLiveDataStatus(){
        playGameLiveData.setValue(PlayGameLiveDataWrapper.loaded(gameDetailsEntity, currentLevel));
    }

    private void setGameCompletedLiveDataStatus(){
        playGameLiveData.setValue(PlayGameLiveDataWrapper.gameCompleted());
    }

    private void setErrorLiveDataStatus(String message){
        playGameLiveData.setValue(PlayGameLiveDataWrapper.error(message));
    }
}
