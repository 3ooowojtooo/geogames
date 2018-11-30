package com.kurocho.geogames;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.kurocho.geogames.api.GameDetails;

import java.util.ArrayList;

public class MyGamesFragment extends Fragment {

    @BindView(R.id.my_games_recycler_view)
    RecyclerView mRecyclerView;

    private GameItemAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<GameDetails> myDataset = new ArrayList<GameDetails>();

    private MainActivity mainActivity;

    public static MyGamesFragment newInstance(int index) {
        MyGamesFragment f = new MyGamesFragment();
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);
        return f;
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
        mAdapter = new GameItemAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setClickListener(v -> {/* TODO open game fragment mainActivity.fragNavController.pushFragment();*/});
        return view;
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


    class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.MyViewHolder> {
        private ArrayList<GameDetails> mDataset;
        View.OnClickListener mClickListener;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class MyViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            RelativeLayout mRelativeLayout;
            public MyViewHolder(RelativeLayout rl) {
                super(rl);
                mRelativeLayout = rl;
            }
        }

        void setClickListener(View.OnClickListener callback) {
            mClickListener = callback;
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public GameItemAdapter(ArrayList<GameDetails> myDataset) {
            mDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public GameItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {
            // create a new view
            RelativeLayout rl = (RelativeLayout) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.my_games_item, parent, false);

            MyViewHolder vh = new MyViewHolder(rl);
            vh.itemView.setOnClickListener(v -> mClickListener.onClick(v));
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            // TODO do what comments above say

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

}
