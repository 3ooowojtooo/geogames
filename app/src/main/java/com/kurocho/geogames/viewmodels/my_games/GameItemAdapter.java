package com.kurocho.geogames.viewmodels.my_games;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.kurocho.geogames.R;
import com.kurocho.geogames.api.GameDetails;
import com.kurocho.geogames.databinding.MyGamesItemBinding;

import java.util.List;

public class GameItemAdapter extends RecyclerView.Adapter<GameItemAdapter.GameDetailsViewHolder> {

    private List<GameDetails> list;
    View.OnClickListener mClickListener;

    class GameDetailsViewHolder extends RecyclerView.ViewHolder {
        final MyGamesItemBinding binding;
        private final Context context;
        private GameDetailsViewModel viewModel;

        GameDetailsViewHolder(MyGamesItemBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        public Context getContext(){
            return context;
        }

        void setViewModel(GameDetailsViewModel viewModel){
            this.viewModel = viewModel;
            binding.setViewModel(viewModel);
        }
    }

    @Override
    public GameDetailsViewHolder onCreateViewHolder(ViewGroup parent,
                                                    int viewType) {
        MyGamesItemBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.my_games_item,
                        parent, false);

        return new GameDetailsViewHolder(binding, parent.getContext());
    }

    @Override
    public void onBindViewHolder(GameDetailsViewHolder holder, int position) {
        GameDetailsViewModel vm = new GameDetailsViewModel(); //TODO check if it's good or bad practice
        vm.setData(list.get(position));
        holder.setViewModel(vm);
        holder.itemView.setOnClickListener(view -> mClickListener.onClick(view));
    }


    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void setList(List<GameDetails> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setClickListener(View.OnClickListener callback) {
        mClickListener = callback;
    }
}
