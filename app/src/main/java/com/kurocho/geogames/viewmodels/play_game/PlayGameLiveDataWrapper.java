package com.kurocho.geogames.viewmodels.play_game;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;

public class PlayGameLiveDataWrapper {

    @NonNull private Status status;
    @Nullable private String message;
    @Nullable private GameDetailsEntity game;
    @Nullable private DecryptedLevelEntity level;

    public PlayGameLiveDataWrapper(@NonNull Status status, @Nullable String message, @Nullable GameDetailsEntity game, @Nullable DecryptedLevelEntity level) {
        this.status = status;
        this.message = message;
        this.game = game;
        this.level = level;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public GameDetailsEntity getGame() {
        return game;
    }

    @Nullable
    public DecryptedLevelEntity getLevel() {
        return level;
    }

    public boolean isIdle(){
        return status == Status.IDLE;
    }

    public boolean isInProgress(){
        return status == Status.IN_PROGRESS;
    }

    public boolean isLoaded(){
        return status == Status.LOADED;
    }

    public boolean isGameCompleted(){
        return status == Status.GAME_COMPLETED;
    }

    public boolean isError(){
        return status == Status.ERROR;
    }

    public static PlayGameLiveDataWrapper idle(){
        return new PlayGameLiveDataWrapper(Status.IDLE, "", null, null);
    }

    public static PlayGameLiveDataWrapper inProgress(){
        return new PlayGameLiveDataWrapper(Status.IN_PROGRESS, "", null, null);
    }

    public static PlayGameLiveDataWrapper loaded(@NonNull GameDetailsEntity game, @NonNull DecryptedLevelEntity level){
        return new PlayGameLiveDataWrapper(Status.LOADED, "", game, level);
    }

    public static PlayGameLiveDataWrapper gameCompleted(){
        return new PlayGameLiveDataWrapper(Status.GAME_COMPLETED, "", null, null);
    }

    public static PlayGameLiveDataWrapper error(String message){
        return new PlayGameLiveDataWrapper(Status.ERROR, message, null, null);
    }

    private enum Status {
        IDLE, IN_PROGRESS, LOADED, GAME_COMPLETED, ERROR
    }
}
