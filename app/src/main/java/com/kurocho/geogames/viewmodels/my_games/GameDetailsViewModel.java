package com.kurocho.geogames.viewmodels.my_games;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.GameDetails;

public class GameDetailsViewModel extends ViewModel {

    private MutableLiveData<GameDetails> data;


    public GameDetailsViewModel() {
        this.data = new MutableLiveData<>();
    }

    public LiveData<GameDetails> getData(){
        if(data == null){
            data = new MutableLiveData<GameDetails>();
        }
        return data;
    }

    public void setData(GameDetails data) {
        this.data.setValue(data);
    }

    public GameDetails getValue(){
        return data.getValue();
    }



}
