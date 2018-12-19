package com.kurocho.geogames.di.sign_in_utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import com.kurocho.geogames.di.qualifiers.SignInSharedPreferences;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public abstract class SignInUtilsModule {

    public static final String SHARED_PREFERENCES_FILENAME = "signin";

    @Provides
    @Singleton
    @SignInSharedPreferences
    static SharedPreferences signInSharedPreferences(@ApplicationContext Context context){
        return context.getSharedPreferences(SHARED_PREFERENCES_FILENAME, Context.MODE_PRIVATE);
    }
}
