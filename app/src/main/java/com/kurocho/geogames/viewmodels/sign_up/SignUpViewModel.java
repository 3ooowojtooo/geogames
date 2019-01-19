package com.kurocho.geogames.viewmodels.sign_up;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.utils.sign_up.SignUpUtils;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {
    private SignUpUtils signUpUtils;
    private MutableLiveData<SignUpLiveDataWrapper> signUpLiveData;

    @Inject
    SignUpViewModel(SignUpUtils signUpUtils){
        if(signUpLiveData == null){
            signUpLiveData = new MutableLiveData<>();
            signUpLiveData.setValue(SignUpLiveDataWrapper.idle());
        }
        if(this.signUpUtils == null)
            this.signUpUtils = signUpUtils;
    }

    public LiveData<SignUpLiveDataWrapper> getSignUpLiveData(){
        return signUpLiveData;
    }

    public void signUp(String username, String password, String email){
        if(signUpLiveData != null && signUpLiveData.getValue() != null){
            if(!signUpLiveData.getValue().isInProgress()){
                setIdleSignUpLiveDataStatus();
                setInProgressSignUpLiveDataStatus();
                performSignUp(username, password, email);
            }
        }
    }

    private void performSignUp(String username, String password, String email){
        SignUpCredentials credentials = new SignUpCredentials(username, password, email);
        signUpUtils.signUp(credentials, new SignUpUtils.SignUpCallback() {
            @Override
            public void onSuccess(String message) {
                setSuccessSignUpLiveDataStatus(message);
            }

            @Override
            public void onError(String message) {
                setErrorSignUpLiveDataStatus(message);
            }
        });
    }

    private void setIdleSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.idle());
    }

    private void setInProgressSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.inProgress());
    }

    private void setSuccessSignUpLiveDataStatus(String message){
        signUpLiveData.setValue(SignUpLiveDataWrapper.success(message));
    }

    private void setErrorSignUpLiveDataStatus(String message){
        signUpLiveData.setValue(SignUpLiveDataWrapper.error(message));
    }


}
