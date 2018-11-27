package com.kurocho.geogames.viewmodels.login;

import com.kurocho.geogames.api.Token;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class LogInLiveDataWrapper {
    @NonNull private LogInLiveDataStatus status;
    @Nullable Integer statusCode;
    @Nullable private Token token;
    @Nullable private Throwable errorThrowable;

    private LogInLiveDataWrapper(@NonNull LogInLiveDataStatus status,
                                 @Nullable Integer statusCode,
                                 @Nullable Token token,
                                 @Nullable Throwable errorThrowable){
        this.status = status;
        this.statusCode = statusCode;
        this.token = token;
        this.errorThrowable = errorThrowable;
    }

    public boolean isIdle(){
        return (status == LogInLiveDataStatus.IDLE);
    }

    public boolean isInProgress(){
        return (status == LogInLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccessful(){
        return (status == LogInLiveDataStatus.SUCCESS);
    }

    public boolean isApiError(){
        return (status == LogInLiveDataStatus.API_ERROR);
    }

    public boolean isInternetError(){
        return (status == LogInLiveDataStatus.INTERNET_ERROR);
    }

    public boolean isError(){
        return (isApiError() || isInternetError());
    }

    public LogInLiveDataStatus getStatus(){
        return status;
    }

    @Nullable
    public Integer getStatusCode(){
        return statusCode;
    }

    @Nullable
    public Token getToken(){
        return token;
    }

    @Nullable
    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

    public static LogInLiveDataWrapper idle(){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.IDLE, null, null, null);
    }

    public static LogInLiveDataWrapper inProgress(){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.IN_PROGRESS, null, null, null);
    }

    public static LogInLiveDataWrapper success(@NonNull Integer statusCode, @NonNull Token token){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.SUCCESS, statusCode, token, null);
    }

    public static LogInLiveDataWrapper apiError(@NonNull Integer statusCode){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.API_ERROR, statusCode, null, null);
    }

    public static LogInLiveDataWrapper internetError(@NonNull Throwable error){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.INTERNET_ERROR, null, null, error);
    }

}
