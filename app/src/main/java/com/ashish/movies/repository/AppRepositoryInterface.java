package com.ashish.movies.repository;

import android.arch.lifecycle.LiveData;

import com.ashish.movies.db.entity.GenreEntity;
import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.repository.api.ApiResponse;
import com.ashish.movies.repository.model.GenreResponse;
import com.ashish.movies.repository.model.MovieCastAndCrewMemberResponse;
import com.ashish.movies.repository.model.MoviesResponse;

import java.util.List;

public interface AppRepositoryInterface {

    LiveData<ApiResponse<MoviesResponse>> getMovies();

    LiveData<List<MoviesEntity>> getMoviesFromDb();

    LiveData<ApiResponse<GenreResponse>> loadGenre();

    LiveData<List<GenreEntity>> getGenreById(List<Integer> genreId);

    LiveData<ApiResponse<MovieCastAndCrewMemberResponse>> getMovieCastCreMemberByMovieId(int movieId);
}
