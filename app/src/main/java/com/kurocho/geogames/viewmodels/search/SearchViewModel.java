package com.kurocho.geogames.viewmodels.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import com.kurocho.geogames.api.download_game.GameRetrievalRequest;
import com.kurocho.geogames.data.search.SearchGameDetails;
import com.kurocho.geogames.data.search.SearchGamesDetailsLiveDataWrapper;
import com.kurocho.geogames.data.search.SearchGamesRepository;
import com.kurocho.geogames.utils.download_game.DownloadGameUtils;
import com.kurocho.geogames.utils.my_games.MyGamesUtils;
import com.kurocho.geogames.utils.sign_in.SignInUtils;

import javax.inject.Inject;
import java.util.ArrayList;

public class SearchViewModel extends ViewModel {

    private SearchGamesRepository repository;
    private SignInUtils signInUtils;
    private DownloadGameUtils downloadGameUtils;
    private MyGamesUtils myGamesUtils;

    private String searchQuery;

    @Inject
    SearchViewModel(SearchGamesRepository repository, SignInUtils signInUtils, DownloadGameUtils downloadGameUtils, MyGamesUtils myGamesUtils){
        this.repository = repository;
        this.signInUtils = signInUtils;
        this.downloadGameUtils = downloadGameUtils;
        this.myGamesUtils = myGamesUtils;

        searchQuery = "";
    }

    public LiveData<SearchGamesDetailsLiveDataWrapper> getGamesDetailsLiveData(){
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

    public void downloadGame(SearchGameDetails gameDetails){
        downloadGameUtils.downloadGame(gameDetails.getGameId(), new DownloadGameUtils.DownloadGameCallback() {
            @Override
            public void onSuccess(GameRetrievalRequest game) {
                myGamesUtils.saveGame(game, new MyGamesUtils.SaveGameCallback() {
                    @Override
                    public void onSuccess() {
                        Log.i("DOWNLOAD", "success-saved");
                    }

                    @Override
                    public void onDuplicate() {
                        Log.i("DOWNLOAD", "duplicated");
                    }
                });
            }

            @Override
            public void onGameNotFound() {
                Log.i("DOWNLOAD", "not found");
            }

            @Override
            public void onInternalServerError() {
                Log.i("DOWNLOAD", "internal server error");
            }

            @Override
            public void onInternetError(String message) {
                Log.i("DOWNLOAD", message);
            }
        });
    }
}
