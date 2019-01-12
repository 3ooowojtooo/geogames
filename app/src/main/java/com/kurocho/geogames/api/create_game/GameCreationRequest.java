package com.kurocho.geogames.api.create_game;

import android.support.annotation.NonNull;
import com.kurocho.geogames.viewmodels.create_game.GameDetailsCreation;
import com.kurocho.geogames.viewmodels.create_game.GameLevelCreation;

import java.util.ArrayList;

public class GameCreationRequest {

    @NonNull private GameDetailsCreation gameDetailsCreation;
    @NonNull private ArrayList<GameLevelCreation> gameLevels;

    public GameCreationRequest(GameDetailsCreation gameDetailsCreation, ArrayList<GameLevelCreation> gameLevels){
        this.gameDetailsCreation = gameDetailsCreation;
        this.gameLevels = gameLevels;
    }

    @NonNull
    public GameDetailsCreation getGameDetailsCreation() {
        return gameDetailsCreation;
    }

    @NonNull
    public ArrayList<GameLevelCreation> getGameLevels() {
        return gameLevels;
    }
}
