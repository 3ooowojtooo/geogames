package com.kurocho.geogames.api.create_game;

import android.support.annotation.NonNull;
import com.kurocho.geogames.viewmodels.create_game.GameDetailsCreation;
import com.kurocho.geogames.viewmodels.create_game.GameLevelCreation;

import java.util.ArrayList;

public class GameCreationRequest {

    @NonNull private GameDetailsCreation gameDetailsCreation;
    @NonNull private GameLevelCreation [] gameLevels;

    public GameCreationRequest(@NonNull GameDetailsCreation gameDetailsCreation, @NonNull ArrayList<GameLevelCreation> gameLevels){
        this.gameDetailsCreation = gameDetailsCreation;
        this.gameLevels = new GameLevelCreation[gameLevels.size()];
        gameLevels.toArray(this.gameLevels);
    }

    @NonNull
    public GameDetailsCreation getGameDetailsCreation() {
        return gameDetailsCreation;
    }

    @NonNull
    public GameLevelCreation[] getGameLevels() {
        return gameLevels;
    }
}
