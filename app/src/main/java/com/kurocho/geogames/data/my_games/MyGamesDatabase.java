package com.kurocho.geogames.data.my_games;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {GameDetailsEntity.class, EncryptedLevelEntity.class, DecryptedLevelEntity.class}, version = 1)
public abstract class MyGamesDatabase extends RoomDatabase {
    public abstract MyGamesDao myGamesDao();
}
