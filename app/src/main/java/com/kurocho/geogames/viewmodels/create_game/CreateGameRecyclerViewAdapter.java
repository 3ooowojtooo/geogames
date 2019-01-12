package com.kurocho.geogames.viewmodels.create_game;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.kurocho.geogames.R;
import com.kurocho.geogames.databinding.CreateGameGameDetailsItemBinding;
import com.kurocho.geogames.databinding.CreateGameLevelDetailsItemBinding;

public class CreateGameRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

   public static class GameDetailsViewHolder extends RecyclerView.ViewHolder {
       CreateGameGameDetailsItemBinding binding;
       GameDetailsViewHolder(CreateGameGameDetailsItemBinding binding){
            super(binding.getRoot());
            this.binding = binding;
        }
   }

  public static class LevelDetailsViewHolder extends RecyclerView.ViewHolder {
      CreateGameLevelDetailsItemBinding binding;

      LevelDetailsViewHolder(CreateGameLevelDetailsItemBinding binding){
          super(binding.getRoot());
          this.binding = binding;
      }

   }

   public static class AddLevelViewHolder extends RecyclerView.ViewHolder {
       Button addLevelButton;

       AddLevelViewHolder(View addLevelView){
           super(addLevelView);
           addLevelButton = addLevelView.findViewById(R.id.add_level_button);
       }
   }

    private static final int TYPE_GAME_DETAILS = 0;
    private static final int TYPE_LEVEL_DETAILS = 1;
    private static final int TYPE_ADD_LEVEL = 2;

    private GameDetailsCreationObservable gameDetailsCreationObservable;
    private ListOfGameLevelCreationObservables gameLevelCreationObservableList;

    public CreateGameRecyclerViewAdapter(GameDetailsCreationObservable gameDetailsCreationObservable,
                                         ListOfGameLevelCreationObservables gameLevelCreationObservableList){
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
            case TYPE_ADD_LEVEL:
                View addLevelLayout = LayoutInflater.from(viewGroup.getContext()).
                        inflate(R.layout.create_game_add_level_item, viewGroup, false);
                return new AddLevelViewHolder(addLevelLayout);
            default:
                throw new RuntimeException("Unknown view holder type: " + String.valueOf(viewType));
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
                    if(gameLevelCreationObservableList.size() > 1) {
                        gameLevelCreationObservableList.remove(levelCreationObservable);
                        notifyDataSetChanged();
                    }
                });
                break;
            case TYPE_ADD_LEVEL:
                AddLevelViewHolder holder3 = (AddLevelViewHolder) viewHolder;
                holder3.addLevelButton.setOnClickListener(v -> {
                    gameLevelCreationObservableList.createAndAppendNewGameLevelObject();
                    notifyDataSetChanged();
                });
                break;
            default:
                throw new RuntimeException("Unknown view holder type: " + String.valueOf(viewHolder.getItemViewType()));
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0) {
            return TYPE_GAME_DETAILS;
        }
       else if(position == (getItemCount()-1)){
            return TYPE_ADD_LEVEL;
        } else {
            return TYPE_LEVEL_DETAILS;
        }
    }

    @Override
    public int getItemCount() {
        return (1 + gameLevelCreationObservableList.size() + 1);
    }
}
