package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.kurocho.geogames.R;
import com.kurocho.geogames.databinding.CreateGameGameDetailsItemBinding;
import com.kurocho.geogames.databinding.CreateGameLevelDetailsItemBinding;

import java.util.List;

public class CreateGameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public static class GameDetailsViewHolder extends RecyclerView.ViewHolder {
       CreateGameGameDetailsItemBinding binding;
        public GameDetailsViewHolder(CreateGameGameDetailsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
   }

  public static class LevelDetailsViewHolder extends RecyclerView.ViewHolder {
      CreateGameLevelDetailsItemBinding binding;

      public LevelDetailsViewHolder(CreateGameLevelDetailsItemBinding binding){
          super(binding.getRoot());
          this.binding = binding;
      }

   }

    private static final int TYPE_GAME_DETAILS = 0;
    private static final int TYPE_LEVEL_DETAILS = 1;
    private static final int TYPE_ADD_LEVEL = 2;

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private List<GameLevelCreationObservable> gameLevelCreationObservableList;

    public CreateGameRecyclerViewAdapter(GameDetailsCreationObservable gameDetailsCreationObservable,
                                         List<GameLevelCreationObservable> gameLevelCreationObservableList){
        super();
        this.gameDetailsCreationObservable = gameDetailsCreationObservable;
        this.gameLevelCreationObservableList = gameLevelCreationObservableList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case TYPE_GAME_DETAILS:
                CreateGameGameDetailsItemBinding binding1 =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.create_game_game_details_item, viewGroup, false);
                return new GameDetailsViewHolder(binding1);
            case TYPE_LEVEL_DETAILS:
                CreateGameLevelDetailsItemBinding binding2 =
                        DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                                R.layout.create_game_level_details_item, viewGroup, false);
                return new LevelDetailsViewHolder(binding2);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        switch(viewHolder.getItemViewType()){
            case TYPE_GAME_DETAILS:
                GameDetailsViewHolder holder1 = (GameDetailsViewHolder) viewHolder;
                holder1.binding.setGameDetailsCreationObservable(gameDetailsCreationObservable);
                break;
            case TYPE_LEVEL_DETAILS:
                LevelDetailsViewHolder holder2 = (LevelDetailsViewHolder) viewHolder;
                GameLevelCreationObservable levelCreationObservable = gameLevelCreationObservableList.get(position-1);
                holder2.binding.setGameLevelCreationObservable(levelCreationObservable);
                holder2.binding.levelDeleteButton.setOnClickListener(v -> {
                    gameLevelCreationObservableList.remove(levelCreationObservable);
                    notifyDataSetChanged();
                });
                break;
            case TYPE_ADD_LEVEL:
                break;
            default:
                break;
        }

    }

    @Override
    public int getItemViewType(int position) {
        Log.i("TYPE", "ABC");
        if(position == 0) {
            Log.i("TYPE", "GD: "+ String.valueOf(position));
            return TYPE_GAME_DETAILS;
        }
       else {
            Log.i("TYPE", "LD: "+ String.valueOf(position));
            return TYPE_LEVEL_DETAILS;
        }
    }

    @Override
    public int getItemCount() {
        return (1 + gameLevelCreationObservableList.size());
    }
}
