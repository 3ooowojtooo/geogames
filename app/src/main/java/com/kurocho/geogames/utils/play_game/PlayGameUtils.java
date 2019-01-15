package com.kurocho.geogames.utils.play_game;

import android.os.AsyncTask;
import android.util.Log;
import com.kurocho.geogames.GeoGamesApplication_MembersInjector;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.data.my_games.MyGamesDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlayGameUtils {

    public interface GetGameAndCurrentLevelCallback{
        void onSuccess(GameDetailsEntity game, EncryptedLevelEntity nextLevel, DecryptedLevelEntity decryptedLevel);
    }

    private MyGamesDatabase database;

    @Inject
    public PlayGameUtils(MyGamesDatabase database){
        this.database = database;
    }

    public void getGameAndCurrentLevel(int gameId, GetGameAndCurrentLevelCallback callback){
        Log.i("PLAY", "pre-async-task: " + gameId);
        new GetGameAndCurrentLevelAsyncTask(database, callback).execute(gameId);
    }

    private static class GameAndLevel{
        GameDetailsEntity game;
        EncryptedLevelEntity encryptedLevel;
        DecryptedLevelEntity decryptedLevel;

        GameAndLevel(GameDetailsEntity game, EncryptedLevelEntity encryptedLevel, DecryptedLevelEntity decryptedLevel){
            this.game = game;
            this.encryptedLevel = encryptedLevel;
            this.decryptedLevel = decryptedLevel;
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
            DecryptedLevelEntity currentLevel = database.myGamesDao().getDecryptedLevelsByGameId(gameId).get(game.getLevelsCompleted());
            EncryptedLevelEntity nextLevel;
            if((game.getLevelsCompleted()+1) == game.getNumbersOfLevels()){
                // last level
                nextLevel = null;
                Log.i("PLAY", "last level, number of levels = " + game.getNumbersOfLevels());
            } else{
                nextLevel = database.myGamesDao().getEncryptedLevelsByGameId(gameId).get(game.getLevelsCompleted()+1);
                Log.i("PLAY", "not last level");
            }
            Log.i("PLAY", "async-task gameId: " + game.getGameId());
            Log.i("PLAY", "async-task-dib pre return");
            return new GameAndLevel(game, nextLevel, currentLevel);
        }

        @Override
        protected void onPostExecute(GameAndLevel gameAndLevel) {
            super.onPostExecute(gameAndLevel);
            Log.i("PLAY", "async-task-ope");
            callback.onSuccess(gameAndLevel.game, gameAndLevel.encryptedLevel, gameAndLevel.decryptedLevel);
        }
    }
}
