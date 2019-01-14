package com.kurocho.geogames.di;

import android.content.Context;
import com.kurocho.geogames.GeoGamesApplication;
import com.kurocho.geogames.di.modules.MyGamesUtilsModule;
import com.kurocho.geogames.views.MainActivity;
import com.kurocho.geogames.di.modules.ApiModule;
import com.kurocho.geogames.di.modules.MainActivityModule;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import com.kurocho.geogames.di.scopes.PerActivity;
import com.kurocho.geogames.di.modules.SignInUtilsModule;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactoryModule;
import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Module(includes = {AndroidSupportInjectionModule.class, ViewModelFactoryModule.class, ApiModule.class, SignInUtilsModule.class, MyGamesUtilsModule.class})
abstract class GeoGamesApplicationModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    abstract MainActivity injectMainActivity();

    @Singleton
    @Binds
    @ApplicationContext
    abstract Context applicationContext(GeoGamesApplication app);

}
