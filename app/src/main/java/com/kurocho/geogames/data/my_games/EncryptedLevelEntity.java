package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "encrypted_levels",
        primaryKeys = {"gameId", "ord"},
        foreignKeys = @ForeignKey(entity = GameDetailsEntity.class, parentColumns = "gameId", childColumns = "gameId", onDelete = CASCADE, onUpdate = CASCADE),
        indices = {@Index(value = {"gameId", "ord"}, unique = true)})
public class EncryptedLevelEntity {

    private int gameId;
    private int ord;
    private String coordinates;
    private String encryptedDescription;
    private String encryptionType;
    private String hashType;

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public String getEncryptedDescription() {
        return encryptedDescription;
    }

    public void setEncryptedDescription(String encryptedDescription) {
        this.encryptedDescription = encryptedDescription;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getHashType() {
        return hashType;
    }

    public void setHashType(String hashType) {
        this.hashType = hashType;
    }

    public int getOrd() {
        return ord;
    }

    public void setOrd(int ord) {
        this.ord = ord;
    }
}
