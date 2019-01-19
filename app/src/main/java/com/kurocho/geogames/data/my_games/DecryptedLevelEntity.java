package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "decrypted_levels",
        primaryKeys = {"gameId", "ord"},
        foreignKeys = @ForeignKey(entity = GameDetailsEntity.class, parentColumns = "gameId", childColumns = "gameId", onDelete = CASCADE, onUpdate = CASCADE),
        indices = {@Index(value = {"gameId", "ord"}, unique = true)})
public class DecryptedLevelEntity {

    private int gameId;
    private int ord;
    private String description;
    private String coordinates;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }
}
