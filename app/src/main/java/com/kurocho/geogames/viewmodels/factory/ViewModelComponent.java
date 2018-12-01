package com.kurocho.geogames.viewmodels.factory;

import com.kurocho.geogames.SignInFragment;
import dagger.Component;

import javax.inject.Singleton;

@Singleton
@Component(modules = ViewModelModule.class)
public interface ViewModelComponent {
    void signInFragmentBinding(SignInFragment fragment);

}
