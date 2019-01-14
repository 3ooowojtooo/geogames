package com.kurocho.geogames.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.api.GameDetails;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.my_games.MyGamesItemAdapter;
import com.kurocho.geogames.viewmodels.my_games.MyGamesViewModel;
import com.kurocho.geogames.views.base_fragment.UnGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;
import java.util.ArrayList;

public class MyGamesFragment extends UnGuardedFragment {

    @BindString(R.string.app_bar_title_my_games)
    String appBarTitle;

    @BindView(R.id.my_games_recycler_view)
    RecyclerView recyclerView;

    private MyGamesViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    private MyGamesItemAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager
            ;
    private MainActivity mainActivity;

    public static MyGamesFragment newInstance() {
        return new MyGamesFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MyGamesViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_games, container, false);
        ButterKnife.bind(this, view);
        initializeRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
            initializeLiveDataObservers();
        } else {
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setBarTitle(appBarTitle);
        viewModel.loadGames();
    }

    private void initializeRecyclerView(){
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewAdapter = new MyGamesItemAdapter(viewModel.getGameDetailsEntities(), this::launchGame);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    private void initializeLiveDataObservers(){
        viewModel.getListMyGamesLiveData().observe(this, wrapper -> {
            if(wrapper != null){
                if(wrapper.isIdle()){
                    processIdleListMyGamesLiveDataStatus();
                } else if(wrapper.isInProgress()){
                    processInProgressListMyGamesLiveDataStatus();
                } else if(wrapper.isSuccess()){
                    processSuccessListMyGamesLiveDataStatus();
                } else if(wrapper.isError()){
                    processErrorListMyGamesLiveDataStatus(wrapper.getMessage());
                }
            }
        });
    }

    private void launchGame(int gameId){
        Toast.makeText(mainActivity, String.valueOf(gameId), Toast.LENGTH_LONG).show();
    }

    private void processIdleListMyGamesLiveDataStatus(){
        mainActivity.hideProgressOverlay();
    }

    private void processInProgressListMyGamesLiveDataStatus(){
        mainActivity.showProgressOverlay();
    }

    private void processSuccessListMyGamesLiveDataStatus(){
        mainActivity.hideProgressOverlay();
        recyclerViewAdapter.notifyDataSetChanged();
    }

    private void processErrorListMyGamesLiveDataStatus(String message){
        mainActivity.hideProgressOverlay();
        // todo
    }


}