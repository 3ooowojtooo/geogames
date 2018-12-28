package com.kurocho.geogames.viewmodels.main_activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.utils.sign_in.SignInUtils;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {

    private SignInUtils signInUtils;

    @Inject
    MainActivityViewModel(SignInUtils signInUtils){
        this.signInUtils = signInUtils;
    }

    LiveData<Boolean> getIsSignedInLiveData(){
        return signInUtils.getIsUserSignedInLiveData();
    }
}
