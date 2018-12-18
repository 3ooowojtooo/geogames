package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.utils.SignInUtils;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {
    private SignInUtils signInUtils;
    private MutableLiveData<SignInLiveDataWrapper> logInLiveData;

    @Inject
    SignInViewModel(SignInUtils signInUtils){
        if(logInLiveData == null) {
            logInLiveData = new MutableLiveData<>();
            logInLiveData.setValue(SignInLiveDataWrapper.idle());
        }
        if(this.signInUtils == null)
            this.signInUtils = signInUtils;
    }

    SignInViewModel(SignInUtils signInUtils, MutableLiveData<SignInLiveDataWrapper> logInLiveData){
        this.signInUtils = signInUtils;
        this.logInLiveData = logInLiveData;
    }

    public LiveData<SignInLiveDataWrapper> getLogInLiveData() {
        return logInLiveData;
    }

    public void signIn(String username, String password){
        if(logInLiveData != null && logInLiveData.getValue() != null){
            if(!logInLiveData.getValue().isInProgress()){
                setIdleSignInLiveDataStatus();
                setInProgressSignInLiveDataStatus();
                performSignIn(username, password);
            }
        }

    }

    private void performSignIn(String username, String password){
        SignInCredentials credentials = new SignInCredentials(username, password);
        signInUtils.signIn(credentials, new SignInUtils.SignInCallback() {
            @Override
            public void onSuccess() {
                setSuccessSignInLiveDataStatus();
            }

            @Override
            public void onError(String message) {
                setErrorSignInLiveDataStatus(message);
            }
        });
    }

    private void setIdleSignInLiveDataStatus(){
        logInLiveData.setValue(SignInLiveDataWrapper.idle());
    }

    private void setInProgressSignInLiveDataStatus(){
        logInLiveData.setValue(SignInLiveDataWrapper.inProgress());
    }

    private void setSuccessSignInLiveDataStatus(){
        logInLiveData.setValue(SignInLiveDataWrapper.success());
    }

    private void setErrorSignInLiveDataStatus(String message){
        logInLiveData.setValue(SignInLiveDataWrapper.error(message));
    }
    /*
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
        setInProgressSignInLiveDataStatus();
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
    }*/
}
