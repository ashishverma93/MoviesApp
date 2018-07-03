package com.ashish.movies.view.Home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.repository.AppRepository;
import com.ashish.movies.repository.api.ApiResponse;
import com.ashish.movies.repository.model.GenreResponse;
import com.ashish.movies.repository.model.MoviesResponse;

import java.util.List;

import javax.inject.Inject;

public class HomeActivityViewModel extends ViewModel {

    private AppRepository repository;

    @Inject
    HomeActivityViewModel(AppRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponse<MoviesResponse>> getMovies() {
        return repository.getMovies();
    }

    public LiveData<List<MoviesEntity>> getMoviesFromDb(){
        return repository.getMoviesFromDb();
    }

    public LiveData<ApiResponse<GenreResponse>> loadGenre() {
        return repository.loadGenre();
    }
}
