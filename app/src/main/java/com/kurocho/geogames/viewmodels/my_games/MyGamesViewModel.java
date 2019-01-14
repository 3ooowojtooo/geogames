package com.kurocho.geogames.viewmodels.my_games;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.utils.my_games.MyGamesUtils;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class MyGamesViewModel extends ViewModel {

    private MyGamesUtils myGamesUtils;
    private List<GameDetailsEntity> gameDetailsEntities;

    private MutableLiveData<ListMyGamesLiveDataWrapper> listMyGamesLiveData;

    @Inject
    MyGamesViewModel(MyGamesUtils myGamesUtils){
        this.myGamesUtils = myGamesUtils;
        gameDetailsEntities = new ArrayList<>();

        listMyGamesLiveData = new MutableLiveData<>();
        setIdleListMyGamesLiveDataStatus();
    }

    public void loadGames(){
        if(listMyGamesLiveData.getValue() != null) {
            if(!listMyGamesLiveData.getValue().isInProgress()) {
                setInProgressListMyGamesLiveDataStatus();
                myGamesUtils.getAllGames((games) -> {
                    gameDetailsEntities.clear();
                    gameDetailsEntities.addAll(games);
                    setSuccessListMyGamesLiveDataStatus();
                    setIdleListMyGamesLiveDataStatus();
                });
            }
        }
    }

    public List<GameDetailsEntity> getGameDetailsEntities(){
        return gameDetailsEntities;
    }

    public LiveData<ListMyGamesLiveDataWrapper> getListMyGamesLiveData(){
        return listMyGamesLiveData;
    }

    private void setIdleListMyGamesLiveDataStatus(){
        listMyGamesLiveData.setValue(ListMyGamesLiveDataWrapper.idle());
    }

    private void setInProgressListMyGamesLiveDataStatus(){
        listMyGamesLiveData.setValue(ListMyGamesLiveDataWrapper.inProgress());
    }

    private void setSuccessListMyGamesLiveDataStatus(){
        listMyGamesLiveData.setValue(ListMyGamesLiveDataWrapper.success());
    }

    private void setErrorListMyGamesLiveDataStatus(String message){
        listMyGamesLiveData.setValue(ListMyGamesLiveDataWrapper.error(message));
    }
}
