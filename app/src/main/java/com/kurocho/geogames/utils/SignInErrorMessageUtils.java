package com.kurocho.geogames.utils;

import android.content.Context;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SignInErrorMessageUtils {

    private Context context;

    private String emptyCredentialsMessage;
    private String incorrectCredentialsMessage;
    private String undefinedApiError;
    private String internetErrorMessage;

    String getEmptyCredentialsMessage(){
        return emptyCredentialsMessage;
    }

    String getApiErrorMessage(int code){
        switch(code){
            case 401:
                return incorrectCredentialsMessage;
                default:
                    return undefinedApiError;
        }
    }

    String getInternetErrorMessage(){
        return internetErrorMessage;
    }


    @Inject
    SignInErrorMessageUtils(@ApplicationContext Context context){
        this.context = context;
        initialize();
    }

    private void initialize(){
        emptyCredentialsMessage = context.getString(R.string.sign_in_empty_credentials);
        incorrectCredentialsMessage = context.getString(R.string.sign_in_incorrect_credentials);
        undefinedApiError = context.getString(R.string.sign_in_undefined_api_error);
        internetErrorMessage = context.getString(R.string.sign_in_internet_error);
    }
}
