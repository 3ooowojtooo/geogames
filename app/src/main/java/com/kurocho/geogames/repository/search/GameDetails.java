package com.kurocho.geogames.repository.search;

public class GameDetails {

    private Timestamp dateCreated;
    private String description;
    private int gameId;
    private String gameType;
    private String inviteUrl;
    private String title;

    public GameDetails(Timestamp dateCreated, String description, int gameId, String gameType, String inviteUrl, String title) {
        this.dateCreated = dateCreated;
        this.description = description;
        this.gameId = gameId;
        this.gameType = gameType;
        this.inviteUrl = inviteUrl;
        this.title = title;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public String getDescription() {
        return description;
    }

    public int getGameId() {
        return gameId;
    }

    public String getGameType() {
        return gameType;
    }

    public String getInviteUrl() {
        return inviteUrl;
    }

    public String getTitle() {
        return title;
    }



    public static class Timestamp{
        private int date;
        private int day;
        private int hours;
        private int minutes;
        private int month;
        private int nans;
        private int seconds;
        private int time;
        private int timezoneOffset;
        private int year;


        public Timestamp(int date, int day, int hours, int minutes, int month, int nans, int seconds, int time, int timezoneOffset, int year) {
            this.date = date;
            this.day = day;
            this.hours = hours;
            this.minutes = minutes;
            this.month = month;
            this.nans = nans;
            this.seconds = seconds;
            this.time = time;
            this.timezoneOffset = timezoneOffset;
            this.year = year;
        }

        public int getDate() {
            return date;
        }

        public int getDay() {
            return day;
        }

        public int getHours() {
            return hours;
        }

        public int getMinutes() {
            return minutes;
        }

        public int getMonth() {
            return month;
        }

        public int getNans() {
            return nans;
        }

        public int getSeconds() {
            return seconds;
        }

        public int getTime() {
            return time;
        }

        public int getTimezoneOffset() {
            return timezoneOffset;
        }

        public int getYear() {
            return year;
        }
    }
}
