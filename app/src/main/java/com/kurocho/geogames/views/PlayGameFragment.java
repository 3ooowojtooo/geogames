package com.kurocho.geogames.views;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.play_game.PlayGameViewModel;
import com.kurocho.geogames.views.base_fragment.UnGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class PlayGameFragment extends UnGuardedFragment {

    @BindView(R.id.game_title)
    TextView title;

    @BindView(R.id.current_level)
    TextView currentLevel;

    @BindView(R.id.number_of_levels)
    TextView numberOfLevels;

    @BindView(R.id.level_description)
    TextView levelDescription;

    private PlayGameViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    private MainActivity mainActivity;

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(PlayGameViewModel.class);
        viewModel.clear();
        obtainGameIdAndInitializeViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.play_game_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
            initializeLiveDataObserver();
        } else {
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
    }

    private void obtainGameIdAndInitializeViewModel(){
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey("gameId")){
            int gameId = arguments.getInt("gameId");
            Log.i("PLAY", String.valueOf(gameId));
            viewModel.initialize(gameId);
        } else {
            throw new RuntimeException("PlayGameFragment: gameId argument not set.");
        }
    }

    private void initializeLiveDataObserver(){
        viewModel.getPlayGameLiveData().observe(this, wrapper -> {
            if(wrapper != null){
                if(wrapper.isIdle()){
                    processIdleLiveDataStatus();
                } else if(wrapper.isInProgress()){
                    processInProgressLiveDataStatus();
                } else if(wrapper.isLoaded()){
                    processLoadedLiveDataStatus(wrapper.getGame(), wrapper.getLevel());
                }
            }
        });
    }

    private void processIdleLiveDataStatus(){
        mainActivity.hideProgressOverlay();
        Log.i("PLAY", "idle");
    }

    private void processInProgressLiveDataStatus(){
        mainActivity.showProgressOverlay();
        Log.i("PLAY", "progress");
    }

    private void processLoadedLiveDataStatus(GameDetailsEntity gameDetails, DecryptedLevelEntity currentLevel){
        Log.i("PLAY", "loaded");
        if(gameDetails != null && currentLevel != null){
            this.title.setText(gameDetails.getTitle());
            this.currentLevel.setText(String.valueOf(gameDetails.getLevelsCompleted()+1));
            this.numberOfLevels.setText(String.valueOf(gameDetails.getNumbersOfLevels()));
            this.levelDescription.setText(currentLevel.getDescription());
        }
        mainActivity.hideProgressOverlay();
    }


}
