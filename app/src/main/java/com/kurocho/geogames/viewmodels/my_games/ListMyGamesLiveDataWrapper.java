package com.kurocho.geogames.viewmodels.my_games;

import android.support.annotation.NonNull;

public class ListMyGamesLiveDataWrapper {

    @NonNull private Status status;
    @NonNull private String message;

    private ListMyGamesLiveDataWrapper(@NonNull Status status, @NonNull String message){
        this.status = status;
        this.message = message;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public boolean isIdle(){
        return (status == Status.IDLE);
    }

    public boolean isInProgress(){
        return (status == Status.IN_PROGRESS);
    }

    public boolean isSuccess(){
        return (status == Status.SUCCESS);
    }

    public boolean isError(){
        return (status == Status.ERROR);
    }

    public static ListMyGamesLiveDataWrapper idle(){
        return new ListMyGamesLiveDataWrapper(Status.IDLE, "");
    }

    public static ListMyGamesLiveDataWrapper inProgress(){
        return new ListMyGamesLiveDataWrapper(Status.IN_PROGRESS, "");
    }

    public static ListMyGamesLiveDataWrapper success(){
        return new ListMyGamesLiveDataWrapper(Status.SUCCESS, "");
    }

    public static ListMyGamesLiveDataWrapper error(String message){
        return new ListMyGamesLiveDataWrapper(Status.ERROR, message);
    }

    private enum Status {
        IDLE, IN_PROGRESS, ERROR, SUCCESS
    }
}
