package com.kurocho.geogames.viewmodels.create_game;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

public class CreateGameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_GAME_DETAILS = 0;
    private static final int TYPE_LEVEL_DETAILS = 1;
    private static final int TYPE_ADD_LEVEL = 2;

    private GameDetailsCreation gameDetailsCreation;
    private List<GameLevelCreation> gameLevelCreationList;

    public CreateGameRecyclerViewAdapter(GameDetailsCreation gameDetailsCreation, List<GameLevelCreation> gameLevelCreationList){
        super();
        this.gameDetailsCreation = gameDetailsCreation;
        this.gameLevelCreationList = gameLevelCreationList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch(viewHolder.getItemViewType()){
            case TYPE_GAME_DETAILS:
                break;
            case TYPE_LEVEL_DETAILS:
                break;
            case TYPE_ADD_LEVEL:
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0){
            return TYPE_GAME_DETAILS;
        } else if(position == (getItemCount() - 1)){
            return TYPE_ADD_LEVEL;
        } else {
            return TYPE_LEVEL_DETAILS;
        }
    }

    @Override
    public int getItemCount() {
        return (1 + gameLevelCreationList.size() + 1);
    }
}
