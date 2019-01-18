package com.kurocho.geogames.utils.create_game;

import com.kurocho.geogames.utils.exception.EmptyCredentialsException;
import com.kurocho.geogames.api.create_game.GameDetailsCreation;
import com.kurocho.geogames.api.create_game.GameLevelCreation;

import javax.inject.Inject;
import java.util.ArrayList;

public class CreateGameDataVerifier {

    private GameDetailsCreation gameDetailsCreation;
    private ArrayList<GameLevelCreation> gameLevelCreations;

    @Inject
    CreateGameDataVerifier(){}

    void verify(GameDetailsCreation gameDetailsCreation, ArrayList<GameLevelCreation> gameLevelCreations) throws EmptyCredentialsException {
        this.gameDetailsCreation = gameDetailsCreation;
        this.gameLevelCreations = gameLevelCreations;
        verifyFieldsNotEmpty();
    }

    private void verifyFieldsNotEmpty() throws EmptyCredentialsException{
        if(gameDetailsCreation.getDescription().isEmpty() || gameDetailsCreation.getTitle().isEmpty())
            throw new EmptyCredentialsException();

        for(GameLevelCreation currentLevel : gameLevelCreations){
            if(currentLevel.getDescription().isEmpty() || currentLevel.getAnswer().isEmpty())
                throw new EmptyCredentialsException();
        }
    }

}
