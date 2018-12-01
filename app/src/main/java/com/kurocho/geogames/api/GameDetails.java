package com.kurocho.geogames.api;

public class GameDetails {
    private String title;
    private String author;
    private Integer progress;
    private Integer numberOfStages;

    public GameDetails(String title, String author, Integer progress, Integer numberOfStages) {
        this.title = title;
        this.author = author;
        this.progress = progress;
        this.numberOfStages = numberOfStages;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getProgressInPercent() {
        return Math.round((progress.floatValue()/numberOfStages.floatValue())*100);
    }
}
