package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.*;
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

    @Insert(onConflict = FAIL)
    abstract void insertDecryptedLevel(DecryptedLevelEntity decryptedLevelEntity);

    @Update
    public abstract void updateGameDetails(GameDetailsEntity gameDetailsEntity);

    @Transaction
    public void insertGame(GameDetailsEntity gameDetailsEntity, List<EncryptedLevelEntity> encryptedLevels, List<DecryptedLevelEntity> decryptedLevels){
        insertGameDetails(gameDetailsEntity);
        insertEncryptedLevels(encryptedLevels);
        insertDecryptedLevels(decryptedLevels);
    }

    @Transaction
    public EncryptedLevelEntity updateGameAndGetNextLevel(GameDetailsEntity gameDetailsEntity, DecryptedLevelEntity decryptedLevelEntity){
        updateGameDetails(gameDetailsEntity);
        insertDecryptedLevel(decryptedLevelEntity);
        return getEncryptedLevel(gameDetailsEntity.getGameId(), gameDetailsEntity.getLevelsCompleted()+1);
    }

    @Query("SELECT * FROM game_details")
    public abstract List<GameDetailsEntity> getAllGames();

    @Query("SELECT * FROM game_details WHERE gameId = :gameId")
    public abstract GameDetailsEntity getGameById(int gameId);

    @Query("SELECT * FROM encrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    public abstract List<EncryptedLevelEntity> getEncryptedLevels(int gameId);

    @Query("SELECT * FROM encrypted_levels WHERE gameId = :gameId AND ord = :ord")
    public abstract EncryptedLevelEntity getEncryptedLevel(int gameId, int ord);

    @Query("SELECT * FROM decrypted_levels WHERE gameId = :gameId ORDER BY ord ASC")
    public abstract List<DecryptedLevelEntity> getDecryptedLevels(int gameId);

    @Query("SELECT * FROM decrypted_levels WHERE gameId = :gameId AND ord = :ord")
    public abstract DecryptedLevelEntity getDecryptedLevel(int gameId, int ord);
}
