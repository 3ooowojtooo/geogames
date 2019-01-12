package com.kurocho.geogames.viewmodels.create_game;

public class GameLevelCreation {

    private String description;
    private String answer;
    private String ord;
    private String coordinates;

    public GameLevelCreation(String description, String answer, String ord){
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

    public String getOrd() {
        return ord;
    }

    public String getCoordinates() {
        return coordinates;
    }
}