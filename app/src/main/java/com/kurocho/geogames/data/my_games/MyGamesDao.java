package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;
import com.kurocho.geogames.api.GameDetails;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.*;

@Dao
public abstract class MyGamesDao {
    @Insert(onConflict = FAIL)
    abstract void insertGameDetails(GameDetailsEntity gameDetails);

    @Insert(onConflict = FAIL)
    abstract void insertEncryptedLevels(List<EncryptedLevelEntity> encryptedLevels);

    @Insert(onConflict = FAIL)
    abstract void insertDecryptedLevels(List<DecryptedLevelEntity> decryptedLevels);

    @Transaction
    public void insertGame(GameDetailsEntity gameDetailsEntity, List<EncryptedLevelEntity> encryptedLevels, List<DecryptedLevelEntity> decryptedLevels){
        insertGameDetails(gameDetailsEntity);
        insertEncryptedLevels(encryptedLevels);
        insertDecryptedLevels(decryptedLevels);
    }

    @Query("SELECT * FROM game_details")
    public abstract List<GameDetailsEntity> getAllGames();

    @Query("SELECT * FROM game_details WHERE gameId = :gameId")
    abstract GameDetailsEntity getGameById(int gameId);

    @Query("SELECT * FROM encrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    abstract List<EncryptedLevelEntity> getEncryptedLevelsByGameId(int gameId);

    @Query("SELECT * FROM decrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    abstract List<DecryptedLevelEntity> getDecryptedLevelsByGameId(int gameId);
}
