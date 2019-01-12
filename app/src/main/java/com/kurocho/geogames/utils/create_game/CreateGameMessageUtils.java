package com.kurocho.geogames.utils.create_game;

import android.content.Context;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;

import javax.inject.Inject;

public class CreateGameMessageUtils {
    private Context context;

    private String emptyFieldsMessage;
    private String successMessage;
    private String internetErrorMessage;

    public String getEmptyFieldsMessage() {
        return emptyFieldsMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getInternetErrorMessage() {
        return internetErrorMessage;
    }

    @Inject
    CreateGameMessageUtils(@ApplicationContext Context context){
        this.context = context;
        initialize();
    }

    private void initialize(){
        this.emptyFieldsMessage = context.getString(R.string.create_game_empty_fields);
        this.successMessage = context.getString(R.string.create_game_success);
        this.internetErrorMessage = context.getString(R.string.create_game_internet_error);
    }
}
