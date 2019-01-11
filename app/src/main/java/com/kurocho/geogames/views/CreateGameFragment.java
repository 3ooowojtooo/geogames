package com.kurocho.geogames.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.create_game.CreateGameRecyclerViewAdapter;
import com.kurocho.geogames.viewmodels.create_game.CreateGameViewModel;
import com.kurocho.geogames.views.base_fragment.SignInGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class CreateGameFragment extends SignInGuardedFragment {

    @BindView(R.id.create_game_recycler_view)
    RecyclerView recyclerView;

    private MainActivity mainActivity;

    private CreateGameViewModel viewModel;

    private CreateGameRecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

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
        View view = inflater.inflate(R.layout.create_game, container, false);
        ButterKnife.bind(this, view);
        initializeRecyclerView();
        return view;
    }

    private void initializeRecyclerView(){
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewAdapter = new CreateGameRecyclerViewAdapter(viewModel.getGameDetailsCreationObservable(), viewModel.getGameLevelCreationObservablesList());
        recyclerView.setAdapter(recyclerViewAdapter);
    }
}
