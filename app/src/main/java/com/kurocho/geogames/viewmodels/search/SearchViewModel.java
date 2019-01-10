package com.kurocho.geogames.viewmodels.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.repository.search.GamesDetailsLiveDataWrapper;
import com.kurocho.geogames.repository.search.SearchGamesRepository;
import com.kurocho.geogames.utils.sign_in.SignInUtils;

import javax.inject.Inject;

public class SearchViewModel extends ViewModel {

    private SearchGamesRepository repository;
    private SignInUtils signInUtils;
    private String searchQuery;

    @Inject
    SearchViewModel(SearchGamesRepository repository, SignInUtils signInUtils){
        this.repository = repository;
        this.signInUtils = signInUtils;
        searchQuery = "";
    }

    public LiveData<GamesDetailsLiveDataWrapper> getGamesDetailsLiveData(){
        return repository.getGameDetailsLiveData();
    }

    public void loadGamesDetails(){
        repository.loadGameDetails(searchQuery);
    }

    public String getSearchQuery(){
        return searchQuery;
    }

    public void setSearchQuery(String query){
        searchQuery = query;
    }

    public boolean canCreateGame(){
        return signInUtils.isUserSignedIn();
    }
}
