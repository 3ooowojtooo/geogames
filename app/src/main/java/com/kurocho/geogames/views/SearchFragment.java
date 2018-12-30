package com.kurocho.geogames.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.Toast;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.repository.search.GameDetails;
import com.kurocho.geogames.repository.search.GamesDetailsLiveDataWrapper;
import com.kurocho.geogames.viewmodels.search.SearchItemAdapter;
import com.kurocho.geogames.viewmodels.search.SearchViewModel;
import com.kurocho.geogames.views.base_fragment.UnGuardedFragment;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;
import java.util.List;

public class SearchFragment extends UnGuardedFragment implements SearchView.OnQueryTextListener {

    @BindString(R.string.app_bar_title_search)
    String appBarTitle;

    @BindView(R.id.search_recycler_view)
    RecyclerView recyclerView;

    private SearchItemAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager recyclerViewLayoutManager;

    private MainActivity mainActivity;

    private SearchViewModel viewModel;

    @Inject
    ViewModelFactory viewModelFactory;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        ButterKnife.bind(this, view);
        initializeRecyclerView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
            initializeViewModelObserver();
        } else {
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setBarTitle(appBarTitle);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(this);
        sv.setIconifiedByDefault(false);
        sv.setOnSearchClickListener(view -> Log.v("SEARCH","Clicked: "));
        super.onCreateOptionsMenu(menu,inflater);
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v("SEARCH","Submitted: "+query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v("SEARCH","Changed: "+newText);
        return false;
    }

    private void initializeRecyclerView(){
        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(recyclerViewLayoutManager);
        recyclerViewAdapter = new SearchItemAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        viewModel.loadGamesDetails("Asd");
    }

    private void initializeViewModelObserver(){
        viewModel.getGamesDetailsLiveData().observe(this, new Observer<GamesDetailsLiveDataWrapper>() {
            @Override
            public void onChanged(@Nullable GamesDetailsLiveDataWrapper wrapper) {
                if(wrapper != null){
                    if(wrapper.isIdle()){
                        processIdleLiveDataStatus();
                    } else if(wrapper.isInProgress()){
                        processInProgressLiveDataStatus();
                    } else if(wrapper.isError()){
                        processErrorLiveDataStatus(wrapper.getErrorMessage());
                    } else if(wrapper.isSuccess()){
                        processSuccessLiveDataStatus(wrapper.getData());
                    }
                }
            }
        });
    }

    private void processIdleLiveDataStatus(){
        mainActivity.hideProgressOverlay();
    }

    private void processInProgressLiveDataStatus(){
        mainActivity.showProgressOverlay();
    }

    private void processErrorLiveDataStatus(String message){
        mainActivity.hideProgressOverlay();
        Toast.makeText(mainActivity, message, Toast.LENGTH_LONG).show();
    }

    private void processSuccessLiveDataStatus(List<GameDetails> data){
        mainActivity.hideProgressOverlay();
        if(data != null){
            recyclerViewAdapter.setData(data);
        }
    }



}
