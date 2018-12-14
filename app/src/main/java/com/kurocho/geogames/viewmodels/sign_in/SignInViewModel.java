package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.api.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {
    private Api api;
    private MutableLiveData<SignInLiveDataWrapper> logInLiveData;

    @Inject
    SignInViewModel(Api api){
        if(logInLiveData == null) {
            logInLiveData = new MutableLiveData<>();
            logInLiveData.setValue(SignInLiveDataWrapper.idle());
        }
        if(this.api == null)
            this.api = api;
    }

    SignInViewModel(Api api, MutableLiveData<SignInLiveDataWrapper> logInLiveData){
        this.api = api;
        this.logInLiveData = logInLiveData;
    }

    public LiveData<SignInLiveDataWrapper> getLogInLiveData() {
        return logInLiveData;
    }

    public void login(String username, String password){
        if(logInLiveData != null && logInLiveData.getValue() != null){
            if(!logInLiveData.getValue().isInProgress()){
                setIdleLogInLiveDataStatus();
                retrofitLogin(username, password);
            }
        }

    }

    private void setIdleLogInLiveDataStatus(){
        logInLiveData.setValue(SignInLiveDataWrapper.idle());
    }

    private void setInProgressLogInLiveDataStatus(){
        logInLiveData.setValue(SignInLiveDataWrapper.inProgress());
    }

    private void setSuccessfulLogInLiveDataStatus(@NonNull Integer statusCode, @NonNull Token token){
        logInLiveData.setValue(SignInLiveDataWrapper.success(statusCode, token));
    }

    private void setApiErrorLogInLiveDataStatus(@NonNull Integer statusCode){
        logInLiveData.setValue(SignInLiveDataWrapper.apiError(statusCode));
    }

    private void setInternetErrorLogInLiveDataStatus(@NonNull Throwable error){
        logInLiveData.setValue(SignInLiveDataWrapper.internetError(error));
    }

    private void retrofitLogin(String username, String password){
        setInProgressLogInLiveDataStatus();
        SignInCredentials credentials = new SignInCredentials(username, password);
        api.signIn(credentials).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(@NonNull  Call<Void> call, @NonNull Response<Void> response) {
                        if(response.isSuccessful()){
                            Integer statusCode = response.code();
                            Token token = new Token(response.headers().get("Authorization"));
                            setSuccessfulLogInLiveDataStatus(statusCode, token);
                        } else{
                            Integer statusCode  = response.code();
                            setApiErrorLogInLiveDataStatus(statusCode);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull  Call<Void> call, @NonNull Throwable t) {
                        setInternetErrorLogInLiveDataStatus(t);
                    }
                });
    }
}
