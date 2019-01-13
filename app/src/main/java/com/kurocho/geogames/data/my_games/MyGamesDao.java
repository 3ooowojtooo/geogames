package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MyGamesDao {
    @Insert
    void insertGame(GameDetailsEntity gameDetails);

    @Insert
    void insertEncrytptedLevels(List<EncryptedLevelEntity> encryptedLevels);

    @Insert
    void insertDecryptedLevels(List<DecryptedLevelEntity> decryptedLevels);

    @Query("SELECT * FROM game_details")
    List<GameDetailsEntity> getAllGames();

    @Query("SELECT * FROM game_details WHERE gameId = :gameId")
    GameDetailsEntity getGameById(int gameId);

    @Query("SELECT * FROM encrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    List<EncryptedLevelEntity> getEncryptedLevelsByGameId(int gameId);

    @Query("SELECT * FROM decrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    List<DecryptedLevelEntity> getDecryptedLevelsByGameId(int gameId);
}
