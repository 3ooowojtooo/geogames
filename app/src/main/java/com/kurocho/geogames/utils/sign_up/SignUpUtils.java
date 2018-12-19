package com.kurocho.geogames.utils.sign_up;

import android.support.annotation.Nullable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.sign_up.SignUpApiResponse;
import com.kurocho.geogames.api.sign_up.SignUpCredentials;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;
import okhttp3.ResponseBody;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;

@Singleton
public class SignUpUtils {

    public interface SignUpCallback{
        void onSuccess(String message);
        void onError(String message);
    }

    private SignUpMessageUtils messageUtils;
    private SignUpCredentialsVerifier credentialsVerifier;
    private Api api;

    private SignUpCredentials credentials;
    private SignUpCallback callback;

    @Inject
    SignUpUtils(SignUpMessageUtils messageUtils,
                SignUpCredentialsVerifier credentialsVerifier,
                Api api){
        this.messageUtils = messageUtils;
        this.credentialsVerifier = credentialsVerifier;
        this.api = api;
    }

    public void signUp(SignUpCredentials credentials, SignUpCallback callback){
        this.credentials = credentials;
        this.callback = callback;

        verifyCredentialsAndPerformSignUp();
    }

    private void verifyCredentialsAndPerformSignUp(){
        try{
            verifyCredentials();
            performSignUp();
        } catch(EmptyCredentialsException e){
            processEmptyCredentialsException();
        }
    }

    private void verifyCredentials() throws EmptyCredentialsException{
        credentialsVerifier.verify(credentials);
    }

    private void performSignUp(){
        api.signUp(credentials).enqueue(new Callback<SignUpApiResponse>() {
            @Override
            public void onResponse(@NotNull Call<SignUpApiResponse> call, @NotNull Response<SignUpApiResponse> response) {
                if(response.isSuccessful()){
                    SignUpApiResponse body = response.body();

                    if(body != null && body.isSuccess()){
                        processSuccessfulResponse(body.getMessage());
                    } else{
                        processInternalServerErrorResponse();
                    }
                } else{
                    SignUpApiResponse errorBody = jsonErrorBodyToObject(response.errorBody());

                    if(errorBody != null && errorBody.isFailure()){
                        processApiErrorResponse(errorBody.getMessage());
                    } else{
                        processInternalServerErrorResponse();
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<SignUpApiResponse> call, @NotNull Throwable t) {
                processInternetErrorResponse();
            }
        });

    }

    private void processEmptyCredentialsException(){
        String message = messageUtils.getEmptyCredentialsMessage();
        callback.onError(message);
    }

    private void processSuccessfulResponse(String message){
        callback.onSuccess(message);
    }

    private void processInternalServerErrorResponse(){
        String message = messageUtils.getApiErrorMessage();
        callback.onError(message);
    }

    private void processApiErrorResponse(String message){
        callback.onError(message);
    }

    private void processInternetErrorResponse(){
        String message = messageUtils.getInternetErrorMessage();
        callback.onError(message);
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
