package com.kurocho.geogames.viewmodels.sign_up;

import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.Api;

import javax.inject.Inject;

public class SignUpViewModel extends ViewModel {
    private Api api;

    @Inject
    SignUpViewModel(Api api){
        if(this.api == null)
            this.api = api;
    }
}
