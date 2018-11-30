package com.kurocho.geogames.api;

public class GameDetails {
    private String title;
    private String author;
    private int progress = 0;
    private int numberOfStages;

    public GameDetails(String title, String author, int progress, int numberOfStages) {
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

    public int getProgressInPercent() {
        return progress/numberOfStages;
    }
}
