package com.kurocho.geogames.di.modules;

import com.kurocho.geogames.views.CreateGameFragment;
import com.kurocho.geogames.views.SearchFragment;
import com.kurocho.geogames.views.SignInFragment;
import com.kurocho.geogames.views.SignUpFragment;
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
}

