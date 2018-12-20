package com.kurocho.geogames.viewmodels.sign_up;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataStatus;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataWrapper;
import org.jetbrains.annotations.NotNull;

public class SignUpLiveDataWrapper {
    @NonNull private SignUpLiveDataStatus status;
    @NotNull private String message;

    private SignUpLiveDataWrapper(@NotNull SignUpLiveDataStatus status,
                                  @NotNull String message){
        this.status = status;
        this.message = message;
    }

    @NotNull
    public String getMessage(){
        return message;
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

    public boolean isError(){
        return (status == SignUpLiveDataStatus.ERROR);
    }

    static SignUpLiveDataWrapper idle(){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.IDLE, "");
    }

    static SignUpLiveDataWrapper inProgress(){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.IN_PROGRESS, "");
    }

    static SignUpLiveDataWrapper success(@NotNull String message){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.SUCCESS, message);
    }

    static SignUpLiveDataWrapper error(@NotNull String message){
        return new SignUpLiveDataWrapper(SignUpLiveDataStatus.ERROR, message);
    }

}
