package com.kurocho.geogames.viewmodels.search;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
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


    private MutableLiveData<DownloadGameLiveDataWrapper> downloadGameLiveData;

    @Inject
    SearchViewModel(SearchGamesRepository repository, SignInUtils signInUtils, DownloadGameUtils downloadGameUtils, MyGamesUtils myGamesUtils){
        this.repository = repository;
        this.signInUtils = signInUtils;
        this.downloadGameUtils = downloadGameUtils;
        this.myGamesUtils = myGamesUtils;

        searchQuery = "";
        downloadGameLiveData = new MutableLiveData<>();
        setIdleDownloadGameLiveDataStatus();

    }

    public LiveData<SearchGamesDetailsLiveDataWrapper> getGamesDetailsLiveData(){
        return repository.getGameDetailsLiveData();
    }

    public LiveData<DownloadGameLiveDataWrapper> getDownloadGameLiveData(){
        return downloadGameLiveData;
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
        if(downloadGameLiveData.getValue() != null) {
            if(!downloadGameLiveData.getValue().isInProgress()) {
                setInProgressDownloadGameLiveDataStatus();
                downloadGameUtils.downloadGame(gameDetails.getGameId(), new DownloadGameUtils.DownloadGameCallback() {
                    @Override
                    public void onSuccess(GameRetrievalRequest game) {
                        myGamesUtils.saveGame(game, new MyGamesUtils.SaveGameCallback() {
                            @Override
                            public void onSuccess() {
                                setSuccessDownloadGameLiveDataStatus("Game has been added to My Games.");
                                setIdleDownloadGameLiveDataStatus();
                            }

                            @Override
                            public void onDuplicate() {
                                setErrorDownloadGameLiveDataStatus("Game already added to My Games.");
                                setIdleDownloadGameLiveDataStatus();
                            }
                        });
                    }

                    @Override
                    public void onGameNotFound() {
                        setErrorDownloadGameLiveDataStatus("Game not found.");
                        setIdleDownloadGameLiveDataStatus();
                    }

                    @Override
                    public void onInternalServerError() {
                        setErrorDownloadGameLiveDataStatus("Internal server error");
                        setIdleDownloadGameLiveDataStatus();
                    }

                    @Override
                    public void onInternetError(String message) {
                        setErrorDownloadGameLiveDataStatus("Check your internet connection.");
                        setIdleDownloadGameLiveDataStatus();
                    }
                });
            }
        }
    }

    private void setIdleDownloadGameLiveDataStatus(){
        downloadGameLiveData.setValue(DownloadGameLiveDataWrapper.idle());
    }

    private void setInProgressDownloadGameLiveDataStatus(){
        downloadGameLiveData.setValue(DownloadGameLiveDataWrapper.inProgress());
    }

    private void setSuccessDownloadGameLiveDataStatus(String message){
        downloadGameLiveData.setValue(DownloadGameLiveDataWrapper.success(message));
    }

    private void setErrorDownloadGameLiveDataStatus(String message){
        downloadGameLiveData.setValue(DownloadGameLiveDataWrapper.error(message));
    }
}
