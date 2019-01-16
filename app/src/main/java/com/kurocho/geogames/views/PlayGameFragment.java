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

import android.widget.*;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.R;
import com.kurocho.geogames.data.my_games.DecryptedLevelEntity;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.play_game.PlayGameViewModel;
import com.kurocho.geogames.views.base_fragment.UnGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class PlayGameFragment extends UnGuardedFragment {

    @BindView(R.id.game_level_layout)
    LinearLayout gameLevelLayout;

    @BindView(R.id.game_completed)
    TextView gameCompleted;

    @BindView(R.id.game_title)
    TextView title;

    @BindView(R.id.current_level)
    TextView currentLevel;

    @BindView(R.id.number_of_levels)
    TextView numberOfLevels;

    @BindView(R.id.level_description)
    TextView levelDescription;

    @BindView(R.id.level_answer)
    EditText levelAnswer;

    @BindString(R.string.app_bar_title_play_game)
    String appBarTitle;

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

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setBarTitle(appBarTitle);
    }

    private void obtainGameIdAndInitializeViewModel(){
        Bundle arguments = getArguments();
        if(arguments != null && arguments.containsKey("gameId")){
            int gameId = arguments.getInt("gameId");
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
                } else if(wrapper.isGameCompleted()){
                    processCompletedLiveDataStatus();
                } else if(wrapper.isError()){
                    processErrorLiveDataStatus(wrapper.getMessage());
                }
            }
        });
    }

    @OnClick(R.id.check_answer)
    public void checkAnswer(View v){
        String answer = levelAnswer.getText().toString();
        viewModel.decryptCurrentLevel(answer);
    }

    private void processIdleLiveDataStatus(){
        mainActivity.hideProgressOverlay();
    }

    private void processInProgressLiveDataStatus(){
        mainActivity.showProgressOverlay();
    }

    private void processLoadedLiveDataStatus(GameDetailsEntity gameDetails, DecryptedLevelEntity currentLevel){
        if(gameDetails != null && currentLevel != null){
            this.gameLevelLayout.setVisibility(View.VISIBLE);
            this.gameCompleted.setVisibility(View.GONE);

            this.title.setText(gameDetails.getTitle());
            this.currentLevel.setText(String.valueOf(gameDetails.getLevelsCompleted()+1));
            this.numberOfLevels.setText(String.valueOf(gameDetails.getNumbersOfLevels()-1));
            this.levelDescription.setText(currentLevel.getDescription());

            this.levelAnswer.setText("");
        }
        mainActivity.hideProgressOverlay();
    }

    private void processCompletedLiveDataStatus(){
        this.gameLevelLayout.setVisibility(View.GONE);
        this.gameCompleted.setVisibility(View.VISIBLE);
        mainActivity.hideProgressOverlay();
    }

    private void processErrorLiveDataStatus(String message){
        mainActivity.hideProgressOverlay();
        Toast.makeText(mainActivity, message, Toast.LENGTH_LONG).show();
    }


}
