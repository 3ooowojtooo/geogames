package com.kurocho.geogames.viewmodels.sign_up;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class SignUpLiveDataWrapper {
    @NonNull private SignUpLiveDataStatus status;
    @Nullable private Integer statusCode;
    @Nullable private String message;
    @Nullable private Throwable errorThrowable;

    private SignUpLiveDataWrapper(@NonNull SignUpLiveDataStatus status,
                                  @Nullable Integer statusCode,
                                  @Nullable String message,
                                  @Nullable Throwable errorThrowable) {
        this.status = status;
        this.statusCode = statusCode;
        this.message = message;
        this.errorThrowable = errorThrowable;
    }

    public boolean isIdle(){
        return (status == SignUpLiveDataStatus.IDLE);
    }

    public boolean isInProgress(){
        return (status == SignUpLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccess(){
        return (status == SignUpLiveDataStatus.SUCCESS);
    }

    public boolean isApiError(){
        return (status == SignUpLiveDataStatus.API_ERROR);
    }

    public boolean isInternetError(){
        return (status == SignUpLiveDataStatus.INTERNET_ERROR);
    }

    @NonNull
    public SignUpLiveDataStatus getStatus() {
        return status;
    }

    @Nullable
    public Integer getStatusCode() {
        return statusCode;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    @Nullable
    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

    static SignUpLiveDataWrapper idle(){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.IDLE, null, null, null);
    }

    static SignUpLiveDataWrapper inProgress(){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.IN_PROGRESS, null, null, null);
    }

    static SignUpLiveDataWrapper success(@NonNull Integer statusCode, @NonNull String message){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.SUCCESS, statusCode, message, null);
    }

    static SignUpLiveDataWrapper apiError(@NonNull Integer statusCode, @NonNull String message){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.API_ERROR, statusCode, message, null);
    }

    static SignUpLiveDataWrapper internetError(@NonNull Throwable error){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.INTERNET_ERROR, null, null, error);
    }
}
