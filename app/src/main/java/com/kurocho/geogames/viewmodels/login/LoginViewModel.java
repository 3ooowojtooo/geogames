package com.kurocho.geogames.viewmodels.login;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.ApiInstance;
import com.kurocho.geogames.api.Credentials;
import com.kurocho.geogames.api.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {
    private Api api;
    private MutableLiveData<LogInLiveDataWrapper> logInLiveData;

    public LoginViewModel(){
        if(logInLiveData == null) {
            logInLiveData = new MutableLiveData<>();
        }
        if(api == null)
            api = ApiInstance.getInstance();
    }

    public LiveData<LogInLiveDataWrapper> getLogInLiveData() {
        return logInLiveData;
    }

    public void login(String username, String password){
        if(logInLiveData != null && logInLiveData.getValue() != null){

            if(!logInLiveData.getValue().isInProgress()){
                resetLogInLiveDataStatus();
                retrofitLogin(username, password);
            }
        }

    }

    private void resetLogInLiveDataStatus(){
        logInLiveData.setValue(LogInLiveDataWrapper.idle());
    }

    private void retrofitLogin(String username, String password){
        Credentials credentials = new Credentials(username, password);
        api.signIn(credentials).
                enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            String tokenString = response.headers().get("Authorization");
                            Token token = new Token(tokenString);
                            logInLiveData.setValue(LogInLiveDataWrapper.success(token));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }
}
