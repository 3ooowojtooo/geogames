package com.kurocho.geogames.di.viewmodel_factory;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.widget.SearchView;
import com.kurocho.geogames.viewmodels.create_game.CreateGameViewModel;
import com.kurocho.geogames.viewmodels.main_activity.MainActivityViewModel;
import com.kurocho.geogames.viewmodels.my_games.MyGamesViewModel;
import com.kurocho.geogames.viewmodels.play_game.PlayGameViewModel;
import com.kurocho.geogames.viewmodels.search.SearchViewModel;
import com.kurocho.geogames.viewmodels.sign_in.SignInViewModel;
import com.kurocho.geogames.viewmodels.sign_up.SignUpViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel.class)
    abstract ViewModel bindMainActivityViewModel(MainActivityViewModel mainActivityViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel.class)
    abstract ViewModel bindSignInViewModel(SignInViewModel signInViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SignUpViewModel.class)
    abstract ViewModel bindSignUpViewModel(SignUpViewModel signUpViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel.class)
    abstract ViewModel bindSearchViewModel(SearchViewModel searchViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateGameViewModel.class)
    abstract ViewModel bindCreateGameViewModel(CreateGameViewModel createGameViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MyGamesViewModel.class)
    abstract ViewModel bindMyGamesViewModel(MyGamesViewModel myGamesViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PlayGameViewModel.class)
    abstract ViewModel bindPlayGameViewModel(PlayGameViewModel playGameViewModel);
}