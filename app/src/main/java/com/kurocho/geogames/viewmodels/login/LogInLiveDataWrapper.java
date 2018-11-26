package com.kurocho.geogames.viewmodels.login;

import com.kurocho.geogames.api.Token;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class LogInLiveDataWrapper {
    @NonNull private LogInLiveDataStatus status;
    @Nullable private Token token;
    @Nullable private Throwable errorThrowable;

    private LogInLiveDataWrapper(@NonNull LogInLiveDataStatus status, @Nullable Token token,
                                 @Nullable Throwable errorThrowable){
        this.status = status;
        this.token = token;
        this.errorThrowable = errorThrowable;
    }

    public boolean errorOccured(){
        return (status == LogInLiveDataStatus.ERROR_API ||
            status == LogInLiveDataStatus.ERROR_DEVICE);
    }

    public boolean isInProgress(){
        return (status == LogInLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccessful(){
        return (status == LogInLiveDataStatus.SUCCESS);
    }

    public Token getToken(){
        return token;
    }

    public Throwable getErrorThrowable() {
        return errorThrowable;
    }

    public static LogInLiveDataWrapper idle(){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.IDLE, null, null);
    }

    public static LogInLiveDataWrapper success(@NonNull Token token){
        return new LogInLiveDataWrapper(LogInLiveDataStatus.SUCCESS, token, null);
    }

}
