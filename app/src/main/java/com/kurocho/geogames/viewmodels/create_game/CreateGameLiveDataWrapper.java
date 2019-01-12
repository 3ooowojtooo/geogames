package com.kurocho.geogames.viewmodels.create_game;

import android.support.annotation.NonNull;

public class CreateGameLiveDataWrapper {
    @NonNull private CreateGameLiveDataStatus status;
    @NonNull private String message;

    private CreateGameLiveDataWrapper(@NonNull CreateGameLiveDataStatus status, String message){
        this.status = status;
        this.message = message;
    }

    @NonNull
    public String getMessage(){
        return message;
    }

    public boolean isIdle(){
        return (status == CreateGameLiveDataStatus.IDLE);
    }

    public boolean isInProgress(){
        return (status == CreateGameLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccess(){
        return (status == CreateGameLiveDataStatus.SUCCESS);
    }

    public boolean isError(){
        return (status == CreateGameLiveDataStatus.ERROR);
    }

    static CreateGameLiveDataWrapper idle(){
        return new CreateGameLiveDataWrapper(CreateGameLiveDataStatus.IDLE, "");
    }

    static CreateGameLiveDataWrapper inProgress(){
        return new CreateGameLiveDataWrapper(CreateGameLiveDataStatus.IN_PROGRESS, "");
    }

    static CreateGameLiveDataWrapper success(String message){
        return new CreateGameLiveDataWrapper(CreateGameLiveDataStatus.SUCCESS, message);
    }

    static CreateGameLiveDataWrapper error(String errorMessage){
        return new CreateGameLiveDataWrapper(CreateGameLiveDataStatus.ERROR, errorMessage);
    }

}
