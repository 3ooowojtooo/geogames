package com.kurocho.geogames.viewmodels.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.repository.search.GamesDetailsLiveDataWrapper;
import com.kurocho.geogames.repository.search.SearchGamesRepository;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private SearchGamesRepository repository;

    @Inject
    SearchViewModel(SearchGamesRepository repository){
        this.repository = repository;
    }

    public LiveData<GamesDetailsLiveDataWrapper> getGamesDetailsLiveData(){
        return repository.getGameDetailsLiveData();
    }

    public void loadGamesDetails(String query){
        repository.loadGameDetails(query);
    }


}
