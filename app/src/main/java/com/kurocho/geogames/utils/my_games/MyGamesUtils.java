package com.kurocho.geogames.utils.my_games;

import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import com.kurocho.geogames.api.download_game.GameRetrievalRequest;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.EncryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.data.my_games.MyGamesDatabase;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
public class MyGamesUtils {

    public interface SaveGameCallback{
        void onSuccess();
        void onDuplicate();
    }

    public interface GetAllGamesCallback{
        void onSuccess(List<GameDetailsEntity> games);
    }

    private MyGamesDatabase database;

    @Inject
    MyGamesUtils(MyGamesDatabase database){
        this.database = database;
    }

    public void saveGame(GameRetrievalRequest gameRetrievalRequest, SaveGameCallback callback){
        new SaveGameAsyncTask(database, callback).execute(gameRetrievalRequest);
    }

    public void getAllGames(GetAllGamesCallback callback){
        new GetAllGamesAsyncTask(database, callback).execute();
    }


    private static class SaveGameAsyncTask extends AsyncTask<GameRetrievalRequest, Void, SaveGameStatus>{

        private SaveGameCallback callback;
        private MyGamesDatabase database;

        SaveGameAsyncTask(MyGamesDatabase database, SaveGameCallback callback) {
            super();
            this.database = database;
            this.callback = callback;
        }

        @Override
        protected SaveGameStatus doInBackground(GameRetrievalRequest... gameRetrievalRequests) {
            GameRetrievalRequest gameRetrievalRequest = gameRetrievalRequests[0];
            GameDetailsEntity gameDetailsEntity = GameDetailsEntity.fromGameDetails(gameRetrievalRequest.getGameDetails(),
                    0,
                    gameRetrievalRequest.getGameLevels().length);
            List<EncryptedLevelEntity> encryptedLevelEntities = Arrays.asList(gameRetrievalRequest.getGameLevels());
            List<DecryptedLevelEntity> decryptedLevelEntities = new ArrayList<>();
            DecryptedLevelEntity firstLevel = new DecryptedLevelEntity();
            firstLevel.setOrd(encryptedLevelEntities.get(0).getOrd());
            firstLevel.setGameId(encryptedLevelEntities.get(0).getGameId());
            firstLevel.setCoordinates(encryptedLevelEntities.get(0).getCoordinates());
            firstLevel.setDescription(encryptedLevelEntities.get(0).getEncryptedDescription());
            decryptedLevelEntities.add(firstLevel);

            try {
                database.myGamesDao().insertGame(gameDetailsEntity, encryptedLevelEntities, decryptedLevelEntities);
            } catch (SQLiteConstraintException e){
                return SaveGameStatus.DUPLICATED;
            }
            return SaveGameStatus.SUCCESS;
        }

        @Override
        protected void onPostExecute(SaveGameStatus status) {
            super.onPostExecute(status);
            switch(status){
                case SUCCESS:
                    callback.onSuccess();
                    break;
                case DUPLICATED:
                    callback.onDuplicate();
                    break;
            }
        }
    }

    private enum SaveGameStatus{
        SUCCESS, DUPLICATED
    }

    private static class GetAllGamesAsyncTask extends AsyncTask<Void, Void, List<GameDetailsEntity>>{
        private MyGamesDatabase database;
        private GetAllGamesCallback callback;

        GetAllGamesAsyncTask(MyGamesDatabase database, GetAllGamesCallback callback){
            this.database = database;
            this.callback = callback;
        }

        @Override
        protected List<GameDetailsEntity> doInBackground(Void... voids) {
            return database.myGamesDao().getAllGames();
        }

        @Override
        protected void onPostExecute(List<GameDetailsEntity> gameDetailsEntities) {
            callback.onSuccess(gameDetailsEntities);
        }
    }
}
