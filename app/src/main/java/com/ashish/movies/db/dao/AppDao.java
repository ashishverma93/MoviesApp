package com.ashish.movies.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.ashish.movies.db.entity.GenreEntity;
import com.ashish.movies.db.entity.MoviesEntity;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToDb(List<MoviesEntity> moviesEntities);

    @Query("Select * from movies")
    LiveData<List<MoviesEntity>> getMoviesFromDb();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertGenreToDb(List<GenreEntity> genreEntities);

    @Query("SELECT * FROM genre WHERE genreId IN (:genreIdOne)")
    LiveData<List<GenreEntity>> getGenreByGenreId(List<Integer> genreIdOne);
}
