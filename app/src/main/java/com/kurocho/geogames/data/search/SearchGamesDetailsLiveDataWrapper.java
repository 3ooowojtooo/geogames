package com.kurocho.geogames.data.search;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SearchGamesDetailsLiveDataWrapper {

    private enum State{
        SUCCESS, ERROR, IN_PROGRESS, IDLE
    }

    @NotNull
    private State state;

    @Nullable
    private List<SearchGameDetails> data;

    @Nullable
    private String errorMessage;

    private SearchGamesDetailsLiveDataWrapper(@NotNull State state, @Nullable List<SearchGameDetails> data, @Nullable String errorMessage){
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
    public List<SearchGameDetails> getData(){
        return data;
    }

    @Nullable
    public String getErrorMessage(){
        return errorMessage;
    }

    public static SearchGamesDetailsLiveDataWrapper success(@NotNull List<SearchGameDetails> data){
        return new SearchGamesDetailsLiveDataWrapper(State.SUCCESS, data, null);
    }

    public static SearchGamesDetailsLiveDataWrapper error(String message){
        return new SearchGamesDetailsLiveDataWrapper(State.ERROR, null,message);
    }

    public static SearchGamesDetailsLiveDataWrapper inProgress(){
        return new SearchGamesDetailsLiveDataWrapper(State.IN_PROGRESS, null, null);
    }

    public static SearchGamesDetailsLiveDataWrapper idle(){
        return new SearchGamesDetailsLiveDataWrapper(State.IDLE, null, null);
    }

}
