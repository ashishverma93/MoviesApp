package com.ashish.movies.repository.api;

import com.ashish.movies.repository.model.GenreResponse;
import com.ashish.movies.repository.model.MovieCastAndCrewMemberResponse;
import com.ashish.movies.repository.model.MovieVideoResponse;
import com.ashish.movies.repository.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/{type}")
    Call<MoviesResponse> getMovies(@Path(value = "type", encoded = true) String type,
                                   @Query("language") String language,
                                   @Query("page") int page);


    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(@Query("language") String language);

    @GET("movie/{movieId}/credits")
    Call<MovieCastAndCrewMemberResponse> getMovieCastAndCrewByMovieId(@Path(value = "movieId", encoded = true) int movieId, @Query("language") String language);

    @GET("movie/{movieId}/videos")
    Call<MovieVideoResponse> getMovieVideoByMovieId(@Path(value = "movieId", encoded = true) int movieId, @Query("language") String language);
}
