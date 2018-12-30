package com.kurocho.geogames.repository.search;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

@Singleton
public class SearchGamesRepository {

    @Inject
    public SearchGamesRepository(){}

    public List<GameDetails> getExampleGameDetails(){
        GameDetails game1 = new GameDetails(new GameDetails.Timestamp(0, 2, 3, 2, 4, 5, 6, 7, 8, 9),
                "This is super op description", 1, "PUBLIC", "www.google.pl", "Saple game 1");
        return Arrays.asList(game1);
    }
}