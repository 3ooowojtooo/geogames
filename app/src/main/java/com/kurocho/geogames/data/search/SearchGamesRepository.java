package com.kurocho.geogames.data.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import com.kurocho.geogames.api.Api;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class SearchGamesRepository {

    private MutableLiveData<SearchGamesDetailsLiveDataWrapper> gameDetailsLiveData;
    private SearchCache cache;
    private Api api;

    @Inject
    SearchGamesRepository(SearchCache cache, Api api){
        this.cache = cache;
        this.api = api;
        gameDetailsLiveData = new MutableLiveData<>();
        setIdleLiveDataStatus();
    }

    public LiveData<SearchGamesDetailsLiveDataWrapper> getGameDetailsLiveData(){
        return gameDetailsLiveData;
    }

    public void loadGameDetails(String query){
        if(gameDetailsLiveData.getValue() != null) {
            if(!gameDetailsLiveData.getValue().isInProgress()) {
                if(query.isEmpty()){
                    setIdleLiveDataStatus();
                } else {
                    setInProgressLiveDataStatus();

                    if (cache.isQueryCached(query)) {
                        setSuccessLiveDataStatus(cache.getCachedData());
                    } else {
                        loadGameDetailsFromAPI(query);
                    }
                }
            }
        }
    }

    private void loadGameDetailsFromAPI(String query){
        api.getPublicGames(query).enqueue(new Callback<List<SearchGameDetails>>() {
            @Override
            public void onResponse(@NonNull Call<List<SearchGameDetails>> call, @NonNull Response<List<SearchGameDetails>> response) {
                if(response.isSuccessful()){
                    List<SearchGameDetails> body = response.body();
                    if(body != null){
                        processSuccessResponse(query, body);
                    } else{
                        processErrorResponse("Internal server error");
                    }
                } else{
                    processErrorResponse("Api error: " + String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<SearchGameDetails>> call, @NonNull Throwable t) {
                processErrorResponse("Check your internet connection.");
            }
        });
    }

    private void processSuccessResponse(String query, @NonNull List<SearchGameDetails> games){
        cache.store(query, games);
        setSuccessLiveDataStatus(games);
    }

    private void processErrorResponse(String message){
        setErrorLiveDataStatus(message);
        setIdleLiveDataStatus();
    }

    private void setIdleLiveDataStatus(){
        gameDetailsLiveData.setValue(SearchGamesDetailsLiveDataWrapper.idle());
    }

    private void setInProgressLiveDataStatus(){
        gameDetailsLiveData.setValue(SearchGamesDetailsLiveDataWrapper.inProgress());
    }

    private void setSuccessLiveDataStatus(List<SearchGameDetails> data){
        gameDetailsLiveData.setValue(SearchGamesDetailsLiveDataWrapper.success(data));
    }

    private void setErrorLiveDataStatus(String message){
        gameDetailsLiveData.setValue(SearchGamesDetailsLiveDataWrapper.error(message));
    }

}