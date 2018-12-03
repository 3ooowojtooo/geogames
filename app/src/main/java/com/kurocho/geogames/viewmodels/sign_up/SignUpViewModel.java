package com.kurocho.geogames.viewmodels.sign_up;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.sign_up.ApiSignUpResponse;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {
    private Api api;
    private MutableLiveData<SignUpLiveDataWrapper> signUpLiveData;

    @Inject
    SignUpViewModel(Api api){
        if(signUpLiveData == null){
            signUpLiveData = new MutableLiveData<>();
            signUpLiveData.setValue(SignUpLiveDataWrapper.idle());
        }
        if(this.api == null)
            this.api = api;
    }

    public LiveData<SignUpLiveDataWrapper> getSignUpLiveData(){
        return signUpLiveData;
    }

    public void signUp(@NonNull String username, @NonNull String password, @NonNull String email){
        if(signUpLiveData != null && signUpLiveData.getValue() != null){
            if(!signUpLiveData.getValue().isInProgress()){
                setIdleSignUpLiveDataStatus();
                retrofitSignUp(username, password, email);
            }
        }
    }

    private void retrofitSignUp(@NonNull String username,  @NonNull String password, @NonNull String email){
        setInProgressSignUpLiveDataStatus();
        SignUpCredentials credentials = new SignUpCredentials(username, password, email);
        api.signUp(credentials).
                enqueue(new Callback<ApiSignUpResponse>() {
                    @Override
                    public void onResponse(@NonNull  Call<ApiSignUpResponse> call, @NonNull Response<ApiSignUpResponse> response) {
                        // todo
                    }

                    @Override
                    public void onFailure(@NonNull Call<ApiSignUpResponse> call, @NonNull Throwable t) {
                        // todo
                    }
                });
    }

    private void setIdleSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.idle());
    }

    private void setInProgressSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.inProgress());
    }


}
