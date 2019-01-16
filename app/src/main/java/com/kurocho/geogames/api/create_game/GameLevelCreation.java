package com.kurocho.geogames.api.create_game;

public class GameLevelCreation {

    private String description;
    private String answer;
    private int ord;
    private String coordinates;

    public GameLevelCreation(String description, String answer, int ord){
        this.description = description;
        this.answer = answer;
        this.ord = ord;
        this.coordinates = "0;0";
    }

    public String getDescription() {
        return description;
    }

    public String getAnswer() {
        return answer;
    }

    public int getOrd() {
        return ord;
    }

    public String getCoordinates() {
        return coordinates;
    }
}