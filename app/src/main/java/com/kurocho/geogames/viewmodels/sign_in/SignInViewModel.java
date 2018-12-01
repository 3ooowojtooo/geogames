package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.ApiInstance;
import com.kurocho.geogames.api.Credentials;
import com.kurocho.geogames.api.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {
    private Api api;
    private MutableLiveData<SignInLiveDataWrapper> logInLiveData;

    @Inject
    public SignInViewModel(){
        if(logInLiveData == null) {
            logInLiveData = new MutableLiveData<>();
            logInLiveData.setValue(SignInLiveDataWrapper.idle());
        }
        if(api == null)
            api = ApiInstance.getInstance();
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
        Credentials credentials = new Credentials(username, password);
        api.signIn(credentials).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
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
                    public void onFailure(Call<Void> call, Throwable t) {
                        setInternetErrorLogInLiveDataStatus(t);
                    }
                });
    }
}
