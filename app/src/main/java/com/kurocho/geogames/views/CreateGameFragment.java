package com.kurocho.geogames.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.*;

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

    @BindView(R.id.create_game_layout)
    CoordinatorLayout createGameLayout;

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
        setHasOptionsMenu(true);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_create_game, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.create_game:
                viewModel.createGame();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public static class SnackbarPushUpViewBehaviour extends CoordinatorLayout.Behavior<RecyclerView> {

        public SnackbarPushUpViewBehaviour(){
            super();
        }

        public SnackbarPushUpViewBehaviour(Context context, AttributeSet attr){
            super(context, attr);
        }

        @Override
        public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
            return dependency instanceof Snackbar.SnackbarLayout;
        }

        @Override
        public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull RecyclerView child, @NonNull View dependency) {
            float translationY = Math.min(0, dependency.getTranslationY() - dependency.getHeight());
            child.setTranslationY(translationY);
            return true;
        }
    }
}
