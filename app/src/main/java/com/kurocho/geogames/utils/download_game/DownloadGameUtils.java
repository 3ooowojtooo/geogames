package com.kurocho.geogames.utils.download_game;

import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.download_game.GameRetrievalRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DownloadGameUtils {

    public interface DownloadGameCallback{
        void onSuccess(GameRetrievalRequest game);
        void onGameNotFound();
        void onInternalServerError();
        void onInternetError(String message);
    }

    private Api api;

    @Inject
    public DownloadGameUtils(Api api){
        this.api = api;
    }

    public void downloadGame(int gameId, DownloadGameCallback callback){
        api.getGame(gameId).enqueue(new Callback<GameRetrievalRequest>() {
            @Override
            public void onResponse(@NonNull Call<GameRetrievalRequest> call, @NonNull Response<GameRetrievalRequest> response) {
                if(response.isSuccessful()){
                    GameRetrievalRequest body = response.body();
                    if(body != null){
                        callback.onSuccess(body);
                    } else{
                        callback.onInternalServerError();
                    }
                } else{
                    int code = response.code();
                    if(code == 404){
                        callback.onGameNotFound();
                    } else{
                        callback.onInternalServerError();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<GameRetrievalRequest> call, @NonNull Throwable t) {
                callback.onInternetError(t.getMessage());
            }
        });
    }
}
