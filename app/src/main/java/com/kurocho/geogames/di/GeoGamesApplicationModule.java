package com.kurocho.geogames.di;

import android.content.Context;
import com.kurocho.geogames.GeoGamesApplication;
import com.kurocho.geogames.MainActivity;
import com.kurocho.geogames.di.api.ApiModule;
import com.kurocho.geogames.di.main_activity.MainActivityModule;
import com.kurocho.geogames.di.qualifiers.ApplicationContext;
import com.kurocho.geogames.di.scopes.PerActivity;
import com.kurocho.geogames.di.sign_in_utils.SignInUtilsModule;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactoryModule;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

import javax.inject.Singleton;

@Module(includes = {AndroidSupportInjectionModule.class, ViewModelFactoryModule.class, ApiModule.class, SignInUtilsModule.class})
abstract class GeoGamesApplicationModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    abstract MainActivity injectMainActivity();

    @Singleton
    @Binds
    @ApplicationContext
    abstract Context applicationContext(GeoGamesApplication app);

}
