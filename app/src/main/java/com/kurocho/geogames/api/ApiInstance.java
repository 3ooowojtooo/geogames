package com.kurocho.geogames.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiInstance {
    private static Api instance;

    public static Api getInstance(){
        if(instance == null){
            Retrofit retrofit = new Retrofit.Builder().
                    baseUrl(Api.BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).
                    build();
            instance = retrofit.create(Api.class);
        }
        return instance;
    }
}
