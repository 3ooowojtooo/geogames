package com.kurocho.geogames.api.download_game;

import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetails;

public class GameRetrievalRequest {

    private GameDetails gameDetails;
    private EncryptedLevelEntity[] gameLevels;

    public GameRetrievalRequest(GameDetails gameDetails, EncryptedLevelEntity[] gameLevels) {
        this.gameDetails = gameDetails;
        this.gameLevels = gameLevels;
    }

    public GameDetails getGameDetails() {
        return gameDetails;
    }

    public void setGameDetails(GameDetails gameDetails) {
        this.gameDetails = gameDetails;
    }

    public EncryptedLevelEntity[] getGameLevels() {
        return gameLevels;
    }

    public void setGameLevels(EncryptedLevelEntity[] gameLevels) {
        this.gameLevels = gameLevels;
    }
}
