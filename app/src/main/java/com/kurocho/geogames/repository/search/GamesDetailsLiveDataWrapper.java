package com.kurocho.geogames.repository.search;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GamesDetailsLiveDataWrapper {

    private enum State{
        SUCCESS, ERROR, IN_PROGRESS, IDLE
    }

    @NotNull
    private State state;

    @Nullable
    private List<GameDetails> data;

    @Nullable
    private String errorMessage;

    private GamesDetailsLiveDataWrapper(@NotNull State state, @Nullable List<GameDetails> data, @Nullable String errorMessage){
        this.state = state;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess(){
        return (state == State.SUCCESS);
    }

    public boolean isError(){
        return (state == State.ERROR);
    }

    public boolean isInProgress(){
        return (state == State.IN_PROGRESS);
    }

    public boolean isIdle(){
        return (state == State.IDLE);
    }

    @Nullable
    public List<GameDetails> getData(){
        return data;
    }

    @Nullable
    public String getErrorMessage(){
        return errorMessage;
    }

    public static GamesDetailsLiveDataWrapper success(@NotNull List<GameDetails> data){
        return new GamesDetailsLiveDataWrapper(State.SUCCESS, data, null);
    }

    public static GamesDetailsLiveDataWrapper error(String message){
        return new GamesDetailsLiveDataWrapper(State.ERROR, null,message);
    }

    public static GamesDetailsLiveDataWrapper inProgress(){
        return new GamesDetailsLiveDataWrapper(State.IN_PROGRESS, null, null);
    }

    public static GamesDetailsLiveDataWrapper idle(){
        return new GamesDetailsLiveDataWrapper(State.IDLE, null, null);
    }

}
