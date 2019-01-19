package com.kurocho.geogames.viewmodels.sign_in;

import com.kurocho.geogames.api.Token;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class SignInLiveDataWrapper {
    @NonNull private SignInLiveDataStatus status;
    @NonNull private String message;

    private SignInLiveDataWrapper(@NonNull SignInLiveDataStatus status,
                                  @NonNull String message){
        this.status = status;
        this.message = message;
    }

    @NonNull
    public String getMessage(){
        return message;
    }

    public boolean isIdle(){
        return (status == SignInLiveDataStatus.IDLE);
    }

    public boolean isInProgress(){
        return (status == SignInLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccessful(){
        return (status == SignInLiveDataStatus.SUCCESS);
    }

    public boolean isError(){
        return (status == SignInLiveDataStatus.ERROR);
    }

    public static SignInLiveDataWrapper idle(){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.IDLE,"");
    }

    public static SignInLiveDataWrapper inProgress(){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.IN_PROGRESS, "");
    }

    public static SignInLiveDataWrapper success(){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.SUCCESS,"");
    }

    public static SignInLiveDataWrapper error(String message){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.ERROR, message);
    }







    /*@Nullable private  Integer statusCode;
    @Nullable private Token token;
    @Nullable private Throwable errorThrowable;

    private SignInLiveDataWrapper(@NonNull SignInLiveDataStatus status,
                                  @Nullable Integer statusCode,
                                  @Nullable Token token,
                                  @Nullable Throwable errorThrowable){
        this.status = status;
        this.statusCode = statusCode;
        this.token = token;
        this.errorThrowable = errorThrowable;
    }

    public boolean isIdle(){
        return (status == SignInLiveDataStatus.IDLE);
    }

    public boolean isInProgress(){
        return (status == SignInLiveDataStatus.IN_PROGRESS);
    }

    public boolean isSuccessful(){
        return (status == SignInLiveDataStatus.SUCCESS);
    }

    public boolean isApiError(){
        return (status == SignInLiveDataStatus.API_ERROR);
    }

    public boolean isInternetError(){
        return (status == SignInLiveDataStatus.INTERNET_ERROR);
    }

    public boolean isError(){
        return (isApiError() || isInternetError());
    }

    @NonNull
    public SignInLiveDataStatus getStatus(){
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

    static SignInLiveDataWrapper idle(){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.IDLE, null, null, null);
    }

    static SignInLiveDataWrapper inProgress(){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.IN_PROGRESS, null, null, null);
    }

    static SignInLiveDataWrapper success(@NonNull Integer statusCode, @NonNull Token token){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.SUCCESS, statusCode, token, null);
    }

    static SignInLiveDataWrapper apiError(@NonNull Integer statusCode){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.API_ERROR, statusCode, null, null);
    }

    static SignInLiveDataWrapper internetError(@NonNull Throwable error){
        return new SignInLiveDataWrapper(SignInLiveDataStatus.INTERNET_ERROR, null, null, error);
    }*/

}
