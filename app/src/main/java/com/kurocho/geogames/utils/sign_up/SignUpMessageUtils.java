package com.kurocho.geogames.utils.sign_up;

import android.content.Context;
import android.support.annotation.NonNull;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import com.kurocho.geogames.viewmodels.sign_up.SignUpLiveDataWrapper;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
class SignUpMessageUtils {
    private Context context;

    private String emptyCredentialsMessage;
    private String invalidEmailMessage;
    private String apiErrorMessage;
    private String internetErrorMessage;

    String getEmptyCredentialsMessage(){
        return emptyCredentialsMessage;
    }

    String getApiErrorMessage(){
        return apiErrorMessage;
    }

    String getInternetErrorMessage(){
        return internetErrorMessage;
    }

    String getInvalidEmailMessage(){
        return invalidEmailMessage;
    }

    @Inject
    public SignUpMessageUtils(@ApplicationContext Context context){
        this.context = context;
        initialize();
    }

    private void initialize(){
        emptyCredentialsMessage = context.getString(R.string.create_account_empty_credentials);
        invalidEmailMessage = context.getString(R.string.create_account_invalid_email);
        apiErrorMessage = context.getString(R.string.create_account_undefined_api_error);
        internetErrorMessage = context.getString(R.string.create_account_internet_error);
    }
}
