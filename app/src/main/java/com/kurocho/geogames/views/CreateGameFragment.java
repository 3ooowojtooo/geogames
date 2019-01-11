package com.kurocho.geogames.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurocho.geogames.R;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.create_game.CreateGameViewModel;
import com.kurocho.geogames.views.base_fragment.GuardedFragment;
import com.kurocho.geogames.views.base_fragment.SignInGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class CreateGameFragment extends SignInGuardedFragment {

    private MainActivity mainActivity;

    private CreateGameViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    public static CreateGameFragment newInstance(){
        return new CreateGameFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CreateGameViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
        } else {
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.create_game, container, false);
    }
}
