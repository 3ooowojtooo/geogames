package com.kurocho.geogames.di.api;

import com.kurocho.geogames.api.Api;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public abstract class ApiModule {

    @Singleton
    @Provides
    static Retrofit provideRetrofit(){
        Retrofit retrofit = new Retrofit.Builder().
                baseUrl(Api.BASE_URL).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        return retrofit;
    }

    @Singleton
    @Provides
    static Api provideApi(Retrofit retrofit){
        return retrofit.create(Api.class);
    }
}
