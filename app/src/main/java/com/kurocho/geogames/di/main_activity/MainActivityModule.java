package com.kurocho.geogames.di.main_activity;

import com.kurocho.geogames.SignInFragment;
import com.kurocho.geogames.di.sign_in_fragment.SignInFragmentModule;
import com.kurocho.geogames.di.scopes.PerFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainActivityModule {

    @PerFragment
    @ContributesAndroidInjector(modules = SignInFragmentModule.class)
    abstract SignInFragment injectSignInFragment();
}

