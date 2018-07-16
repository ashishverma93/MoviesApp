package com.ashish.movies.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ashish.movies.db.dao.AppDao;
import com.ashish.movies.db.entity.GenreEntity;
import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.repository.api.ApiInterface;
import com.ashish.movies.repository.api.ApiResponse;
import com.ashish.movies.repository.model.GenreResponse;
import com.ashish.movies.repository.model.MovieCastAndCrewMemberResponse;
import com.ashish.movies.repository.model.MovieImageResponse;
import com.ashish.movies.repository.model.MovieVideoResponse;
import com.ashish.movies.repository.model.MoviesResponse;
import com.ashish.movies.utils.AppConstants;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppRepository implements AppRepositoryInterface {

    private final ApiInterface apiInterface;
    private final AppDao appDao;
    private final Executor executor;

    @Inject
    public AppRepository(ApiInterface apiInterface, AppDao appDao, Executor executor) {
        this.apiInterface = apiInterface;
        this.appDao = appDao;
        this.executor = executor;
    }

    @Override
    public LiveData<ApiResponse<MoviesResponse>> getMovies() {
        final MutableLiveData<ApiResponse<MoviesResponse>> liveData = new MutableLiveData<>();
        Call<MoviesResponse> call = apiInterface.getMovies(AppConstants.SORT_BY_POPULAR, AppConstants.LANGUAGE, AppConstants.PAGE);
        call.enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                if (response.isSuccessful() && response.body().getResults() != null) {
                    liveData.setValue(new ApiResponse<>(response.body()));
                    executor.execute(() -> appDao.insertToDb(response.body().getResults()));
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<List<MoviesEntity>> getMoviesFromDb() {
        return appDao.getMoviesFromDb();
    }

    @Override
    public LiveData<ApiResponse<GenreResponse>> loadGenre() {
        final MutableLiveData<ApiResponse<GenreResponse>> liveData = new MutableLiveData<>();
        Call<GenreResponse> call = apiInterface.getGenres(AppConstants.LANGUAGE);
        call.enqueue(new Callback<GenreResponse>() {
            @Override
            public void onResponse(@NonNull Call<GenreResponse> call, @NonNull Response<GenreResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(new ApiResponse<>(response.body()));
                    executor.execute(() -> appDao.insertGenreToDb(response.body().getGenres()));
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<GenreResponse> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<List<GenreEntity>> getGenreById(List<Integer> genreId) {
        return appDao.getGenreByGenreId(genreId);
    }

    @Override
    public LiveData<ApiResponse<MovieCastAndCrewMemberResponse>> getMovieCastCreMemberByMovieId(int movieId) {
        final MutableLiveData<ApiResponse<MovieCastAndCrewMemberResponse>> liveData = new MutableLiveData<>();
        Call<MovieCastAndCrewMemberResponse> call = apiInterface.getMovieCastAndCrewByMovieId(movieId, AppConstants.LANGUAGE);
        call.enqueue(new Callback<MovieCastAndCrewMemberResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieCastAndCrewMemberResponse> call, @NonNull Response<MovieCastAndCrewMemberResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(new ApiResponse<>(response.body()));
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieCastAndCrewMemberResponse> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<ApiResponse<MovieVideoResponse>> getMovieVideosByMovieId(int movieId) {
        final MutableLiveData<ApiResponse<MovieVideoResponse>> liveData = new MutableLiveData<>();
        Call<MovieVideoResponse> call = apiInterface.getMovieVideoByMovieId(movieId, AppConstants.LANGUAGE);
        call.enqueue(new Callback<MovieVideoResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieVideoResponse> call, @NonNull Response<MovieVideoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(new ApiResponse<>(response.body()));
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieVideoResponse> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }

    @Override
    public LiveData<ApiResponse<MovieImageResponse>> getMovieImagesByMovieId(int movieId) {
        final MutableLiveData<ApiResponse<MovieImageResponse>> liveData = new MutableLiveData<>();
        Call<MovieImageResponse> call = apiInterface.getMovieImagesByMovieId(movieId);
        call.enqueue(new Callback<MovieImageResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieImageResponse> call, @NonNull Response<MovieImageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.setValue(new ApiResponse<>(response.body()));
                } else {
                    liveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieImageResponse> call, Throwable t) {
                liveData.setValue(new ApiResponse<>(t));
            }
        });
        return liveData;
    }
}
