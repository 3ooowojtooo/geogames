package com.kurocho.geogames.data.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import com.kurocho.geogames.data.Timestamp;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class SearchGamesRepository {

    private MutableLiveData<SearchGamesDetailsLiveDataWrapper> gameDetailsLiveData;
    private SearchCache cache;

    @Inject
    SearchGamesRepository(SearchCache cache){
        this.cache = cache;
        gameDetailsLiveData = new MutableLiveData<>();
        setIdleLiveDataStatus();
    }

    public LiveData<SearchGamesDetailsLiveDataWrapper> getGameDetailsLiveData(){
        return gameDetailsLiveData;
    }

    public void loadGameDetails(String query){
        setInProgressLiveDataStatus();

        if(cache.isQueryCached(query)){
            setSuccessLiveDataStatus(cache.getCachedData());
        } else {
            List<SearchGameDetails> data = getExampleGameDetails(); // acquire data from api
            cache.store(query, data);
            setSuccessLiveDataStatus(data);
        }
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

    private List<SearchGameDetails> getExampleGameDetails(){
        SearchGameDetails game1 = new SearchGameDetails(new Timestamp(0, 2, 3, 2, 4, 5, 6, 7, 8, 9),
                "This is super op description", 1, "PUBLIC", "www.google.pl", "Saple game 1");
        return Arrays.asList(game1);
    }

}