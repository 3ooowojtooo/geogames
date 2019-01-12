package com.kurocho.geogames.utils.create_game;

import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.Token;
import com.kurocho.geogames.api.create_game.GameCreationRequest;
import com.kurocho.geogames.utils.exception.EmptyCredentialsException;
import com.kurocho.geogames.utils.exception.TokenNotSetException;
import com.kurocho.geogames.utils.sign_in.SignInUtils;
import com.kurocho.geogames.viewmodels.create_game.GameDetailsCreation;
import com.kurocho.geogames.viewmodels.create_game.GameLevelCreation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import java.util.ArrayList;

public class CreateGameUtils {

    public interface CreateGameCallback{
        void onSuccess(String message);
        void onError(String message);
    }
    private CreateGameDataVerifier dataVerifier;
    private CreateGameMessageUtils messageUtils;
    private SignInUtils signInUtils;
    private Api api;

    private GameDetailsCreation gameDetailsCreation;
    private ArrayList<GameLevelCreation> gameLevelCreations;
    private CreateGameCallback callback;

    @Inject
    public CreateGameUtils(CreateGameDataVerifier dataVerifier, CreateGameMessageUtils messageUtils, SignInUtils signInUtils, Api api){
        this.dataVerifier = dataVerifier;
        this.messageUtils = messageUtils;
        this.signInUtils = signInUtils;
        this.api = api;
    }

    public void createGame(GameDetailsCreation gameDetailsCreation,
                           ArrayList<GameLevelCreation> gameLevelCreations,
                           CreateGameCallback callback){
        this.gameDetailsCreation = gameDetailsCreation;
        this.gameLevelCreations = gameLevelCreations;
        this.callback = callback;

        verifyDataAndPerformCreateGame();
    }

    private void verifyDataAndPerformCreateGame(){
        try{
            verifyData();
            performCreateGame();
        } catch(EmptyCredentialsException e){
            callback.onError(messageUtils.getEmptyFieldsMessage());
        }
    }

    private void verifyData() throws EmptyCredentialsException{
        dataVerifier.verify(gameDetailsCreation, gameLevelCreations);
    }

    private void performCreateGame(){
        String userToken;
        try{
            userToken = signInUtils.getToken().getToken();
        } catch (TokenNotSetException e){
            callback.onError("Token not set"); //todo
            return;
        }

        GameCreationRequest gameCreationRequest = new GameCreationRequest(gameDetailsCreation, gameLevelCreations);

        api.createGame(gameCreationRequest, userToken).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if(response.isSuccessful()){
                    processSuccessfulResponse();
                } else{
                    Integer code = response.code();
                    processErrorResponse(code);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                processInternetErrorResponse();
            }
        });


    }

    private void processSuccessfulResponse(){
        callback.onSuccess(messageUtils.getSuccessMessage());
    }

    private void processErrorResponse(Integer code){
        callback.onError("api error: " + String.valueOf(code));
    }

    private void processInternetErrorResponse(){
        callback.onError(messageUtils.getInternetErrorMessage());
    }

}
