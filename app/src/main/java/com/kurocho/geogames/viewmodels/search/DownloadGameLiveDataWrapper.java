package com.kurocho.geogames.viewmodels.search;

import android.support.annotation.NonNull;

public class DownloadGameLiveDataWrapper {
    private @NonNull Status status;
    private @NonNull String message;

    private DownloadGameLiveDataWrapper(@NonNull Status status, @NonNull String message){
        this.status = status;
        this.message = message;
    }

    @NonNull
    public Status getStatus() {
        return status;
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

    public static DownloadGameLiveDataWrapper idle(){
        return new DownloadGameLiveDataWrapper(Status.IDLE, "");
    }

    public static DownloadGameLiveDataWrapper inProgress(){
        return new DownloadGameLiveDataWrapper(Status.IN_PROGRESS, "");
    }

    public static DownloadGameLiveDataWrapper success(String message){
        return new DownloadGameLiveDataWrapper(Status.SUCCESS, message);
    }

    public static DownloadGameLiveDataWrapper error(String message){
        return new DownloadGameLiveDataWrapper(Status.ERROR, message);
    }

    private enum Status {
        IDLE, SUCCESS, IN_PROGRESS, ERROR
    }
}
