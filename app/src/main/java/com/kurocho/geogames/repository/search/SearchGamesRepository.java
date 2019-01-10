package com.kurocho.geogames.repository.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Singleton
public class SearchGamesRepository {

    private MutableLiveData<GamesDetailsLiveDataWrapper> gameDetailsLiveData;
    private SearchCache cache;

    @Inject
    SearchGamesRepository(SearchCache cache){
        this.cache = cache;
        gameDetailsLiveData = new MutableLiveData<>();
        setIdleLiveDataStatus();
    }

    public LiveData<GamesDetailsLiveDataWrapper> getGameDetailsLiveData(){
        return gameDetailsLiveData;
    }

    public void loadGameDetails(String query){
        setInProgressLiveDataStatus();

        if(cache.isQueryCached(query)){
            setSuccessLiveDataStatus(cache.getCachedData());
        } else {
            List<GameDetails> data = getExampleGameDetails(); // acquire data from api
            cache.store(query, data);
            setSuccessLiveDataStatus(data);
        }
    }

    private void setIdleLiveDataStatus(){
        gameDetailsLiveData.setValue(GamesDetailsLiveDataWrapper.idle());
    }

    private void setInProgressLiveDataStatus(){
        gameDetailsLiveData.setValue(GamesDetailsLiveDataWrapper.inProgress());
    }

    private void setSuccessLiveDataStatus(List<GameDetails> data){
        gameDetailsLiveData.setValue(GamesDetailsLiveDataWrapper.success(data));
    }

    private List<GameDetails> getExampleGameDetails(){
        GameDetails game1 = new GameDetails(new GameDetails.Timestamp(0, 2, 3, 2, 4, 5, 6, 7, 8, 9),
                "This is super op description", 1, "PUBLIC", "www.google.pl", "Saple game 1");
        return Arrays.asList(game1);
    }

}