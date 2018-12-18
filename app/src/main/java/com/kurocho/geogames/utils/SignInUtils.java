package com.kurocho.geogames.utils;

import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.api.Token;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignInUtils{


    public interface SignInCallback{
        void onSuccess();
        void onError(String message);
    }

    private SignInTokenUtils tokenUtils;
    private SignInErrorMessageUtils errorMessageUtils;
    private SignInCredentialsVerifier credentialsVerifier;
    private Api api;

    private SignInCredentials credentials;
    private SignInCallback callback;

    @Inject
    SignInUtils(SignInTokenUtils tokenUtils,
                       SignInErrorMessageUtils errorMessageUtils,
                       SignInCredentialsVerifier credentialsVerifier,
                       Api api){
        this.tokenUtils = tokenUtils;
        this.errorMessageUtils = errorMessageUtils;
        this.credentialsVerifier = credentialsVerifier;
        this.api = api;
    }

    public void signIn(SignInCredentials credentials, SignInCallback callback){
        this.credentials = credentials;
        this.callback = callback;

        verifyCredentialsAndPerformSignIn();
    }

    private void verifyCredentialsAndPerformSignIn(){
        try{
            verifyCredentials();
            performSignIn();

        } catch(EmptyCredentialsException e){
            processEmptyCredentialsException();
        }
    }

    private void verifyCredentials() throws EmptyCredentialsException {
        credentialsVerifier.verify(credentials);
    }

    private void performSignIn(){
        api.signIn(credentials).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    String token = response.headers().get("Authorization");
                    processSuccessfulResponse(token);
                } else{
                    Integer code = response.code();
                    processApiErrorResponse(code);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                processInternetErrorResponse();
            }
        });
    }


    private void processEmptyCredentialsException(){
        String message = errorMessageUtils.getEmptyCredentialsMessage();
        callback.onError(message);
    }

    private void processSuccessfulResponse(String token){
        tokenUtils.setTokenDeletingExistingOne(new Token(token));
        callback.onSuccess();
    }

    private void processApiErrorResponse(int code){
        String message = errorMessageUtils.getApiErrorMessage(code);
        callback.onError(message);
    }

    private void processInternetErrorResponse(){
        String message = errorMessageUtils.getInternetErrorMessage();
        callback.onError(message);
    }


}
