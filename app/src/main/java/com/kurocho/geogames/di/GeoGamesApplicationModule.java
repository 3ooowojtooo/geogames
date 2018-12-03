package com.kurocho.geogames.di;

import com.kurocho.geogames.MainActivity;
import com.kurocho.geogames.di.api.ApiModule;
import com.kurocho.geogames.di.main_activity.MainActivityModule;
import com.kurocho.geogames.di.scopes.PerActivity;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactoryModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Module(includes = {AndroidSupportInjectionModule.class, ViewModelFactoryModule.class, ApiModule.class})
abstract class GeoGamesApplicationModule {

    @ContributesAndroidInjector(modules = MainActivityModule.class)
    @PerActivity
    abstract MainActivity injectMainActivity();

}
