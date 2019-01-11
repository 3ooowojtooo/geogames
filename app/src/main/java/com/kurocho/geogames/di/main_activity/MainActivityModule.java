package com.kurocho.geogames.di.main_activity;

import com.kurocho.geogames.views.CreateGameFragment;
import com.kurocho.geogames.views.SearchFragment;
import com.kurocho.geogames.views.SignInFragment;
import com.kurocho.geogames.views.SignUpFragment;
import com.kurocho.geogames.di.sign_in_fragment.SignInFragmentModule;
import com.kurocho.geogames.di.scopes.PerFragment;
import com.kurocho.geogames.di.sign_up_fragment.SignUpFragmentModule;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = SignInFragmentModule.class)
    abstract SignInFragment injectSignInFragment();

    @PerFragment
    @ContributesAndroidInjector(modules = SignUpFragmentModule.class)
    abstract SignUpFragment injectSignUpFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract SearchFragment injectSearchFragment();

    @PerFragment
    @ContributesAndroidInjector
    abstract CreateGameFragment injectCreateGameFragment();
}

