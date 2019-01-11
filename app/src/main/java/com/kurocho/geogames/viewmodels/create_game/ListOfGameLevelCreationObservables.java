package com.kurocho.geogames.viewmodels.create_game;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ListOfGameLevelCreationObservables extends ArrayList<GameLevelCreationObservable> {

    public ListOfGameLevelCreationObservables(){
        super();
    }

    public void createAndAppendNewGameLevelObject(){
        GameLevelCreationObservable newLevel = new GameLevelCreationObservable(size()+1);
        add(newLevel);
    }

    @Override
    public void clear() {
        super.clear();
        reindexLevelsOrdinalNumbers();
    }

    @Override
    public GameLevelCreationObservable remove(int index) {
        GameLevelCreationObservable removedObject =  super.remove(index);
        reindexLevelsOrdinalNumbers();
        return removedObject;
    }


    @Override
    public boolean remove(@Nullable Object o) {
        boolean removeResult = super.remove(o);
        reindexLevelsOrdinalNumbers();
        return removeResult;
    }

    private final void reindexLevelsOrdinalNumbers(){
        int numberOfLevels = size();
        if(numberOfLevels > 0){
            for(int i = 0; i < numberOfLevels; i++){
                get(i).setOrdInteger(i+1);
            }
        }
    }
}