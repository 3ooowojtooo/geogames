package com.kurocho.geogames.repository.search;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

class SearchCache {
    private String query;
    private ArrayList<GameDetails> data;
    private boolean isCached;

    @Inject
    SearchCache(){
        query = "";
        data = new ArrayList<>();
        isCached = false;
    }

    void store(String query, Collection<? extends GameDetails> data){
        this.query = query;
        this.data.clear();
        this.data.addAll(data);
        this.isCached = true;
    }

    boolean isQueryCached(String query){
        return (this.isCached && this.query.equals(query));
    }

    ArrayList<GameDetails> getCachedData(){
        return data;
    }
}
