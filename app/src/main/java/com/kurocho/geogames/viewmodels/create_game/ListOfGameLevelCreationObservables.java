package com.kurocho.geogames.viewmodels.create_game;


import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class ListOfGameLevelCreationObservables extends ArrayList<GameLevelCreationObservable> {

    ListOfGameLevelCreationObservables(){
        super();
    }

    void createAndAppendNewGameLevelObject(){
        GameLevelCreationObservable newLevel = new GameLevelCreationObservable(size()+1);
        add(newLevel);
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

    ArrayList<GameLevelCreation> toNotObservable(){
        ArrayList<GameLevelCreation> result = new ArrayList<>();
        for(GameLevelCreationObservable currentElement : this){
            result.add(currentElement.toNotObservable());
        }
        return result;
    }


}