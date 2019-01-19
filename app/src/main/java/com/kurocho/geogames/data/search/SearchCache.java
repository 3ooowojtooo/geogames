package com.kurocho.geogames.data.search;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

class SearchCache {
    private String query;
    private ArrayList<SearchGameDetails> data;
    private boolean isCached;

    @Inject
    SearchCache(){
        query = "";
        data = new ArrayList<>();
        isCached = false;
    }

    void store(String query, Collection<? extends SearchGameDetails> data){
        this.query = query;
        this.data.clear();
        this.data.addAll(data);
        this.isCached = true;
    }

    boolean isQueryCached(String query){
        return (this.isCached && this.query.equals(query));
    }

    ArrayList<SearchGameDetails> getCachedData(){
        return data;
    }
}
