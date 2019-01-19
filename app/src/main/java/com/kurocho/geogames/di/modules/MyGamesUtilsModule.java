package com.kurocho.geogames.di.modules;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.annotation.MenuRes;
import com.kurocho.geogames.data.my_games.MyGamesDatabase;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public abstract class MyGamesUtilsModule {

    @Provides
    @Singleton
    static MyGamesDatabase provideMyGamesDatabase(@ApplicationContext Context context){
        return Room.databaseBuilder(context, MyGamesDatabase.class, "mygames-database").build();
    }
}
