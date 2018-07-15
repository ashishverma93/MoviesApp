package com.ashish.movies.view.Details;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ashish.movies.db.entity.GenreEntity;
import com.ashish.movies.repository.AppRepository;
import com.ashish.movies.repository.api.ApiResponse;
import com.ashish.movies.repository.model.MovieCastAndCrewMemberResponse;
import com.ashish.movies.repository.model.MovieVideoResponse;

import java.util.List;

import javax.inject.Inject;

public class MovieDetailsViewModel extends ViewModel {
    private AppRepository repository;

    @Inject
    MovieDetailsViewModel(AppRepository repository) {
        this.repository = repository;
    }

    LiveData<List<GenreEntity>> getGenreById(List<Integer> genreId) {
        return repository.getGenreById(genreId);
    }

    LiveData<ApiResponse<MovieCastAndCrewMemberResponse>> getMovieCastAndCrewByMovieId(int movieId) {
        return repository.getMovieCastCreMemberByMovieId(movieId);
    }

    LiveData<ApiResponse<MovieVideoResponse>> getMovieVideosByMovieId(int movieId) {
        return repository.getMovieVideosByMovieId(movieId);
    }

}
