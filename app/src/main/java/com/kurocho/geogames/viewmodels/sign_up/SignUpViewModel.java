package com.kurocho.geogames.viewmodels.sign_up;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.sign_up.SignUpApiResponse;
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
                enqueue(new Callback<SignUpApiResponse>() {
                    @Override
                    public void onResponse(@NonNull  Call<SignUpApiResponse> call, @NonNull Response<SignUpApiResponse> response) {
                        Integer statusCode = response.code();
                        SignUpApiResponse body = response.body();

                        if(body != null){
                            String message = body.getMessage();

                            if(response.isSuccessful() && body.isSuccess()){
                                setSuccessSignUpLiveDataStatus(statusCode, message);
                            } else{
                                setApiErrorSignUpLiveDataStatus(statusCode, message);
                            }

                        } else{
                            // response body is null. internal server error
                            setApiErrorSignUpLiveDataStatus(statusCode, "Internal server error");
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<SignUpApiResponse> call, @NonNull Throwable error) {
                        setInternetErrorSignUpLiveDataStatus(error);
                    }
                });
    }

    private void setIdleSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.idle());
    }

    private void setInProgressSignUpLiveDataStatus(){
        signUpLiveData.setValue(SignUpLiveDataWrapper.inProgress());
    }

    private void setSuccessSignUpLiveDataStatus(@NonNull Integer statusCode, @NonNull String message){
        signUpLiveData.setValue(SignUpLiveDataWrapper.success(statusCode, message));
    }

    private void setApiErrorSignUpLiveDataStatus(@NonNull Integer statusCode, @NonNull String message){
        signUpLiveData.setValue(SignUpLiveDataWrapper.apiError(statusCode, message));
    }

    private void setInternetErrorSignUpLiveDataStatus(@NonNull Throwable error){
        signUpLiveData.setValue(SignUpLiveDataWrapper.internetError(error));
    }


}
