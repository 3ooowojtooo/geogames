package com.kurocho.geogames.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignInErrorMessageUtils {

    private Context context;

    private String incorrectCredentialsMessage;
    private String apiErrorMessage;
    private String internetErrorMessage;

    @NonNull
    public String getErrorStringMessage(@NonNull SignInLiveDataWrapper wrapper){
        if(wrapper.isApiError()){
            return getStringMessageForApiError(wrapper);
        } else if(wrapper.isInternetError()){
            return getStringMessageForInternetError();
        } else{
            throw new IllegalArgumentException("getErrorStringMessage called for non-error SignInLiveDataWrapper.");
        }
    }

    @NonNull
    private String getStringMessageForApiError(@NonNull SignInLiveDataWrapper wrapper){
        switch(wrapper.getStatusCode()){
            case 401:
                return incorrectCredentialsMessage;
                default:
                return apiErrorMessage;
        }
    }

    @NonNull
    private String getStringMessageForInternetError(){
        return internetErrorMessage;
    }

    @Inject
    public SignInErrorMessageUtils(@ApplicationContext Context context){
        this.context = context;
        initialize();
    }

    private void initialize(){
        incorrectCredentialsMessage = context.getString(R.string.sign_in_incorrect_credentials);
        apiErrorMessage = context.getString(R.string.sign_in_api_error);
        internetErrorMessage = context.getString(R.string.sign_in_internet_error);
    }
}
