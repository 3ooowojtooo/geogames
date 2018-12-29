package com.kurocho.geogames.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.api.GameDetails;
import com.kurocho.geogames.viewmodels.my_games.GameItemAdapter;

import java.util.ArrayList;

public class MyGamesFragment extends Fragment {

    @BindView(R.id.my_games_recycler_view)
    RecyclerView mRecyclerView;
    ArrayList<GameDetails> a = new ArrayList<>();
    private GameItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private MainActivity mainActivity;

    public static MyGamesFragment newInstance() {
        return new MyGamesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.my_games, container, false);
        ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setUpRecyclerViewAdapter();

        if(a.size() == 0){
            // Do usuniecia, pokazowe
            a.add(new GameDetails("Szukanie skrytek na wawelu","testowy autor",2,3));
            a.add(new GameDetails("Rajd elektryka","testowy autor2",9,120));
        }
        mAdapter.setList(a);
        return view;
    }

    private void setUpRecyclerViewAdapter(){
        mAdapter = new GameItemAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(v -> { /* TODO open game fragment mainActivity.fragNavController.pushFragment();*/});
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

}