package com.kurocho.geogames.viewmodels.sign_up;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.sign_up.SignUpApiResponse;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;

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
                        int statusCode = response.code();

                        if(response.isSuccessful()){
                            SignUpApiResponse body = response.body();

                            if(body != null && body.isSuccess()){
                                setSuccessSignUpLiveDataStatus(statusCode, body.getMessage());
                            } else{
                                // code is ok but body is empty or status not equals "success" - internal server error
                                setInternalServerErrorSignUpLiveDataStatus(statusCode);
                            }
                        } else{
                            SignUpApiResponse errorBody = jsonErrorBodyToObject(response.errorBody());

                            if(errorBody != null && errorBody.isFailure()){
                                setApiErrorSignUpLiveDataStatus(statusCode, errorBody.getMessage());
                            } else{
                                // code is failure but error body is empty or status not equals "failure" - internal server error
                                setInternalServerErrorSignUpLiveDataStatus(statusCode);
                            }

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

    private void setInternalServerErrorSignUpLiveDataStatus(@NonNull Integer statusCode){
        signUpLiveData.setValue(SignUpLiveDataWrapper.apiError(statusCode, "Internal server error"));
    }

    private SignUpApiResponse jsonErrorBodyToObject(@Nullable ResponseBody errorBody){
        if(errorBody == null){
            // empty errorBody - internal server error
            return null;
        }

        Gson gson = new GsonBuilder().create();
        try {
            return gson.fromJson(errorBody.string(), SignUpApiResponse.class);
        } catch (IOException e) {
            // invalid json object in errorBody - internal server error
            return null;
        }
    }


}
