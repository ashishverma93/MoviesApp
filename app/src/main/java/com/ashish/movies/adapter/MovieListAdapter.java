package com.ashish.movies.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.movies.R;
import com.ashish.movies.db.entity.MoviesEntity;
import com.ashish.movies.utils.AppConstants;
import com.ashish.movies.utils.AppUtils;
import com.ashish.movies.view.interfaces.MovieListClickInterface;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.MyViewHolder> {
    private Context context;
    private List<MoviesEntity> moviesEntities;
    private final MovieListClickInterface movieListClickInterface;

    public MovieListAdapter(Context context) {
        this.context = context;
        try {
            this.movieListClickInterface = ((MovieListClickInterface) context);
        } catch (Exception e) {
            throw new ClassCastException("Activity must implement MovieListClickInterface");
        }
    }

    public void addMovies(List<MoviesEntity> moviesEntities) {
        this.moviesEntities = moviesEntities;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (moviesEntities == null) {
            return 0;
        } else {
            return moviesEntities.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_movie)
        ImageView mImageView;
        @BindView(R.id.txt_movie_title)
        TextView mTxvMovieTitle;
        @BindView(R.id.txt_movie_release_date)
        TextView mTxvMovieReleaseDate;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(int position) {
            MoviesEntity moviesEntity = moviesEntities.get(position);

            Bitmap placeholder = BitmapFactory.decodeResource(context.getResources(), R.color.colorAccent);
            RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), placeholder);
            roundedBitmapDrawable.setCornerRadius(25F);

            if (moviesEntity.getPosterPath() != null) {
                Glide.with(context)
                        .load(AppConstants.POSTER_BASE_PATH + moviesEntity.getPosterPath())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorAccent))
                        .apply(RequestOptions.bitmapTransform(new RoundedCornersTransformation(25, 0)))
                        .into(mImageView);
            }

            if (moviesEntity.getTitle() != null) {
                mTxvMovieTitle.setText(moviesEntity.getTitle());
            }

            if (moviesEntity.getReleaseDate() != null) {
                mTxvMovieReleaseDate.setText(AppUtils.convertDate(moviesEntity.getReleaseDate(), AppConstants.DF1, AppConstants.DF2));
            }
        }

        @OnClick
        void onClick(View view) {
            MoviesEntity moviesEntity = moviesEntities.get(getAdapterPosition());
            String transitionName = AppConstants.MOVIE_ITEM + String.valueOf(getAdapterPosition());
            movieListClickInterface.onMovieItemClickListener(getAdapterPosition(), moviesEntity, mImageView, transitionName);
        }
    }
}
