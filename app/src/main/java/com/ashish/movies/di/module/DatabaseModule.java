package com.ashish.movies.di.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.ashish.movies.db.dao.AppDao;
import com.ashish.movies.db.database.AppDatabase;
import com.ashish.movies.utils.AppConstants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {
    // --- DATABASE INJECTION ---

    @Provides
    @Singleton
    AppDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, AppConstants.DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    @Singleton
    AppDao provideUserDao(AppDatabase database) {
        return database.appDao();
    }
}
