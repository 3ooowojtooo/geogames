package com.kurocho.geogames.viewmodels.play_game;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.utils.play_game.GameDecryptUtils;
import com.kurocho.geogames.utils.play_game.PlayGameUtils;

import javax.inject.Inject;

public class PlayGameViewModel extends ViewModel {

    private PlayGameUtils playGameUtils;
    private GameDecryptUtils gameDecryptUtils;

    private int gameId;

    private GameDetailsEntity gameDetailsEntity;
    private DecryptedLevelEntity currentLevel;
    private EncryptedLevelEntity nextLevel;

    private MutableLiveData<PlayGameLiveDataWrapper> playGameLiveData;

    @Inject
    PlayGameViewModel(PlayGameUtils playGameUtils, GameDecryptUtils gameDecryptUtils){
        this.playGameUtils = playGameUtils;
        this.gameDecryptUtils = gameDecryptUtils;
        this.playGameLiveData = new MutableLiveData<>();
        clear();
    }

    public void clear(){
        gameDetailsEntity = null;
        currentLevel = null;
        nextLevel = null;
        setIdleLiveDataStatus();
    }

    public void initialize(int gameId){
        this.gameId = gameId;
        loadCurrentGameAndLevel();
    }

    public LiveData<PlayGameLiveDataWrapper> getPlayGameLiveData(){
        return playGameLiveData;
    }

    private void loadCurrentGameAndLevel(){
        if(playGameLiveData.getValue() != null) {
            if(!playGameLiveData.getValue().isInProgress()) {
                setInProgressLiveDataStatus();
                playGameUtils.getGameAndCurrentLevel(gameId, (game, nextLevel, currentLevel) -> {
                    this.gameDetailsEntity = game;
                    this.currentLevel = currentLevel;
                    this.nextLevel = nextLevel;
                    if(this.gameDetailsEntity.isGameCompleted()){
                        setGameCompletedLiveDataStatus();
                    } else{
                        setLoadedLiveDataStatus();
                    }
                });
            }
        }
    }

    public void decryptCurrentLevel(String answer){
        if(playGameLiveData.getValue() != null){
            if(!playGameLiveData.getValue().isInProgress()){
                setInProgressLiveDataStatus();
                gameDecryptUtils.decryptEncryptedLevelEntity(nextLevel, answer, new GameDecryptUtils.DecryptLevelCallback() {
                    @Override
                    public void onSuccess(DecryptedLevelEntity decryptedLevelEntity) {
                        processSuccessfullyDecryptedLevel(decryptedLevelEntity);
                    }

                    @Override
                    public void onIncorrectPassword() {
                        setErrorLiveDataStatus("Incorrect password.");
                        setIdleLiveDataStatus();
                    }
                });
            }
        }
    }

    private void processSuccessfullyDecryptedLevel(DecryptedLevelEntity decryptedLevelEntity){
        this.gameDetailsEntity.setLevelsCompleted(this.gameDetailsEntity.getLevelsCompleted() + 1);
        if(gameDetailsEntity.isGameCompleted()){
            playGameUtils.updateGameDetails(gameDetailsEntity, this::setGameCompletedLiveDataStatus);
        } else{
            this.currentLevel = decryptedLevelEntity;
            playGameUtils.updateGameAndGetNextLevel(this.gameDetailsEntity, decryptedLevelEntity, (nextLevel) -> {
                this.nextLevel = nextLevel;
                setLoadedLiveDataStatus();
            });
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
