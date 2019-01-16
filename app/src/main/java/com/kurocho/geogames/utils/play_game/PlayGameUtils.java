package com.kurocho.geogames.utils.play_game;

import android.os.AsyncTask;
import android.util.Log;
import com.kurocho.geogames.data.my_games.*;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayGameUtils {

    public interface GetGameAndCurrentLevelCallback{
        void onSuccess(GameDetailsEntity game, EncryptedLevelEntity nextLevel, DecryptedLevelEntity decryptedLevel);
    }

    public interface UpdateGameAndGetNextLevelCallback{
        void onSuccess(EncryptedLevelEntity nextLevel);
    }

    public interface UpdateGameCallback{
        void onSuccess();
    }

    private MyGamesDatabase database;

    @Inject
    PlayGameUtils(MyGamesDatabase database){
        this.database = database;
    }

    public void getGameAndCurrentLevel(int gameId, GetGameAndCurrentLevelCallback callback){
        new GetGameAndCurrentLevelAsyncTask(database, callback).execute(gameId);
    }

    public void updateGameAndGetNextLevel(GameDetailsEntity gameDetailsEntity, DecryptedLevelEntity decryptedLevelEntity, UpdateGameAndGetNextLevelCallback callback){
        new UpdateGameAndGetNextLevelAsyncTask(gameDetailsEntity, decryptedLevelEntity, database, callback).execute();
    }

    public void updateGameDetails(GameDetailsEntity gameDetailsEntity, UpdateGameCallback callback){
        new UpdateGameAsyncTask(database, callback).execute(gameDetailsEntity);
    }

    private static class GameAndLevel{
        GameDetailsEntity game;
        EncryptedLevelEntity nextLevel;
        DecryptedLevelEntity currentLevel;

        GameAndLevel(GameDetailsEntity game, EncryptedLevelEntity nextLevel, DecryptedLevelEntity currentLevel){
            this.game = game;
            this.nextLevel = nextLevel;
            this.currentLevel = currentLevel;
        }
    }

    private static class GetGameAndCurrentLevelAsyncTask extends AsyncTask<Integer, Void, GameAndLevel>{
        private MyGamesDatabase database;
        private GetGameAndCurrentLevelCallback callback;

        GetGameAndCurrentLevelAsyncTask(MyGamesDatabase database, GetGameAndCurrentLevelCallback callback){
            this.database = database;
            this.callback = callback;
        }

        @Override
        protected GameAndLevel doInBackground(Integer... integers) {
            int gameId = integers[0];
            GameDetailsEntity game = database.myGamesDao().getGameById(gameId);
            DecryptedLevelEntity currentLevel = null;
            EncryptedLevelEntity nextLevel = null;
            if(!game.isGameCompleted()) {
                currentLevel = database.myGamesDao().getDecryptedLevels(gameId).get(game.getLevelsCompleted());
                nextLevel = database.myGamesDao().getEncryptedLevels(gameId).get(game.getLevelsCompleted() + 1);
            }
            return new GameAndLevel(game, nextLevel, currentLevel);
        }

        @Override
        protected void onPostExecute(GameAndLevel gameAndLevel) {
            super.onPostExecute(gameAndLevel);
            callback.onSuccess(gameAndLevel.game, gameAndLevel.nextLevel, gameAndLevel.currentLevel);
        }
    }

    private static class UpdateGameAndGetNextLevelAsyncTask extends AsyncTask<Void, Void, EncryptedLevelEntity>{
        private MyGamesDatabase database;
        private UpdateGameAndGetNextLevelCallback callback;

        private GameDetailsEntity gameDetailsEntity;
        private DecryptedLevelEntity decryptedLevelEntity;

        UpdateGameAndGetNextLevelAsyncTask(GameDetailsEntity gameDetailsEntity, DecryptedLevelEntity decryptedLevelEntity,
                                           MyGamesDatabase database, UpdateGameAndGetNextLevelCallback callback){
            this.gameDetailsEntity = gameDetailsEntity;
            this.decryptedLevelEntity = decryptedLevelEntity;
            this.database = database;
            this.callback = callback;
        }

        @Override
        protected EncryptedLevelEntity doInBackground(Void ... voids) {
            return database.myGamesDao().updateGameAndGetNextLevel(gameDetailsEntity, decryptedLevelEntity);
        }

        @Override
        protected void onPostExecute(EncryptedLevelEntity encryptedLevelEntity) {
            super.onPostExecute(encryptedLevelEntity);
            callback.onSuccess(encryptedLevelEntity);
        }
    }

    private static class UpdateGameAsyncTask extends AsyncTask<GameDetailsEntity, Void, Void>{
        private MyGamesDatabase database;
        private UpdateGameCallback callback;

        UpdateGameAsyncTask(MyGamesDatabase database, UpdateGameCallback callback){
            this.database = database;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(GameDetailsEntity... gameDetailsEntities) {
            database.myGamesDao().updateGameDetails(gameDetailsEntities[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            callback.onSuccess();
        }
    }
}
