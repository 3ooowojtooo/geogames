package com.kurocho.geogames.viewmodels.my_games;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.kurocho.geogames.R;
import com.kurocho.geogames.data.my_games.GameDetailsEntity;
import com.kurocho.geogames.databinding.MyGamesItemBinding;
import java.util.List;


public class MyGamesItemAdapter extends RecyclerView.Adapter<MyGamesItemAdapter.MyGamesItemViewHolder> {

    static class MyGamesItemViewHolder extends RecyclerView.ViewHolder{
        MyGamesItemBinding binding;

        MyGamesItemViewHolder(MyGamesItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private List<GameDetailsEntity> games;
    private LaunchGameInterface launchGameInterface;

    public MyGamesItemAdapter(List<GameDetailsEntity> games, LaunchGameInterface launchGameInterface){
        super();
        this.games = games;
        this.launchGameInterface = launchGameInterface;
    }

    @NonNull
    @Override
    public MyGamesItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int type) {
        MyGamesItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                        R.layout.my_games_item, viewGroup, false);
        return new MyGamesItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGamesItemViewHolder myGamesItemViewHolder, int position) {
        GameDetailsEntity game = games.get(position);
        myGamesItemViewHolder.binding.setGame(game);
        myGamesItemViewHolder.binding.launchGame.setOnClickListener(v -> {
            launchGameInterface.launchGame(game.getGameId());
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    @FunctionalInterface
    public interface LaunchGameInterface{
        void launchGame(int gameId);
    }
}