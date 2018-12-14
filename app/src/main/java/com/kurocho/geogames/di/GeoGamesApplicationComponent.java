package com.kurocho.geogames.di;

import android.content.Context;
import com.kurocho.geogames.GeoGamesApplication;
import dagger.Binds;
import dagger.Component;
import dagger.android.AndroidInjector;

import javax.inject.Singleton;

@Singleton
@Component(modules = GeoGamesApplicationModule.class)
public interface GeoGamesApplicationComponent extends AndroidInjector<GeoGamesApplication> {
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<GeoGamesApplication>{}

}
