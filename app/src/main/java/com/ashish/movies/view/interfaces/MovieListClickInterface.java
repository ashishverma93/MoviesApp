package com.ashish.movies.view.interfaces;

import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.movies.db.entity.MoviesEntity;

public interface MovieListClickInterface {
    void onMovieItemClickListener(int position, MoviesEntity moviesEntity, ImageView mImageView, String transitionName);
}
