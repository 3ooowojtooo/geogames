package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.utils.sign_in.SignInUtils;

import javax.inject.Inject;

public class SignInViewModel extends ViewModel {
    private SignInUtils signInUtils;
    private MutableLiveData<SignInLiveDataWrapper> signInLiveData;

    @Inject
    SignInViewModel(SignInUtils signInUtils){
        if(signInLiveData == null) {
            signInLiveData = new MutableLiveData<>();
            signInLiveData.setValue(SignInLiveDataWrapper.idle());
        }
        if(this.signInUtils == null)
            this.signInUtils = signInUtils;
    }

    public LiveData<SignInLiveDataWrapper> getSignInLiveData() {
        return signInLiveData;
    }

    public void signIn(String username, String password){
        if(signInLiveData != null && signInLiveData.getValue() != null){
            if(!signInLiveData.getValue().isInProgress()){
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
        signInLiveData.setValue(SignInLiveDataWrapper.idle());
    }

    private void setInProgressSignInLiveDataStatus(){
        signInLiveData.setValue(SignInLiveDataWrapper.inProgress());
    }

    private void setSuccessSignInLiveDataStatus(){
        signInLiveData.setValue(SignInLiveDataWrapper.success());
    }

    private void setErrorSignInLiveDataStatus(String message){
        signInLiveData.setValue(SignInLiveDataWrapper.error(message));
    }
    public void reset(){
        setIdleSignInLiveDataStatus();
    }

}
