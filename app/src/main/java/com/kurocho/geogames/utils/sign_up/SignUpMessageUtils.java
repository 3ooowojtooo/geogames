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

    /*
    @NonNull
    public String getMessage(@NonNull SignUpLiveDataWrapper wrapper){
        if(wrapper.isSuccess()){
            return getStringMessageForSuccess();
        } else if(wrapper.isError()){
            return getErrorMessage(wrapper);
        } else{
            throw new IllegalArgumentException("getMessage called for not success nor error message.");
        }
    }

    @NonNull
    private String getStringMessageForSuccess(){
        return accountCreatedMessage;
    }

    @NonNull
    private String getErrorMessage(@NonNull SignUpLiveDataWrapper wrapper){
        if(wrapper.isApiError()){
            return getStringMessageForApiError(wrapper);
        } else if(wrapper.isInternetError()){
            return getStringMessageForInternetError();
        } else{
            throw new IllegalArgumentException("getErrorMessage called for non error SignUpLiveDataWrapper.");
        }
    }

    @NonNull
    private String getStringMessageForApiError(@NonNull SignUpLiveDataWrapper wrapper){
        if(wrapper.getStatusCode() != null) {
            switch (wrapper.getStatusCode()) {
                case 400:
                    return wrapper.getMessage(); // ignore warning, message is never null in case of api error
                default:
                    return apiErrorMessage;
            }
        } else{
            throw new IllegalArgumentException("getStringMessageForApiError called for wrapper with null status code.");
        }
    }

    @NonNull
    private String getStringMessageForInternetError(){
        return internetErrorMessage;
    }*/

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
