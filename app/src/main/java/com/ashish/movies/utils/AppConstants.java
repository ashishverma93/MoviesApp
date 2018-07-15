package com.ashish.movies.utils;

public class AppConstants {
    public static String API_KEY_PARAM = "api_key";
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String DB_NAME = "movies.db";
    public static final String TABLE_MOVIE = "movies";
    public static final String TABLE_GENRE = "genre";

    public static final String LANGUAGE = "en-US";
    public static final int PAGE = 1;
    public static final String POSTER_BASE_PATH = "http://image.tmdb.org/t/p/w185";
    public static final String BACKDROP_BASE_PATH = "http://image.tmdb.org/t/p/w780";
    public static final String MOVIE_ITEM = "movie_item_";
    public static final String SORT_BY_POPULAR = "popular";

    public static final String MOVIE_DETAILS_PARCELABLE = "movie_details_parcelable";
    public static final String MOVIE_IMAGE_TRANSITION = "movie_transition_name";
    public static final String MOVIE_IMAGE_TITLE = "movie_transition_title";
    public static final String MOVIE_IMAGE_DATE = "movie_transition_date";

    // Date Formats
    public static final String DF1 = "yyyy-MM-dd";
    public static final String DF2 = "MMM dd, yyyy";
    public static final String DF3 = "yyyy";

    // Movie Video URL
    public static final String YOUTUBE_THUMBNAIL = "https://img.youtube.com/vi/%s/mqdefault.jpg";
}
