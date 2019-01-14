package com.kurocho.geogames.di.modules;

import com.kurocho.geogames.views.*;
import com.kurocho.geogames.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector
    abstract SignInFragment injectSignInFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract SignUpFragment injectSignUpFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract SearchFragment injectSearchFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract CreateGameFragment injectCreateGameFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract MyGamesFragment injectMyGamesFragment();
}

