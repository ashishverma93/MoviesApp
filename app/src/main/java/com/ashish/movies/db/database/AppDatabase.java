package com.ashish.movies.db.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.ashish.movies.db.Converter.IntegerListConverter;
import com.ashish.movies.db.dao.AppDao;
import com.ashish.movies.db.entity.GenreEntity;
import com.ashish.movies.db.entity.MoviesEntity;

@Database(entities = {MoviesEntity.class, GenreEntity.class}, version = 1, exportSchema = false)
@TypeConverters(IntegerListConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    // --- SINGLETON ---
    private static volatile AppDatabase INSTANCE;

    // --- DAO ---
    public abstract AppDao appDao();
}
