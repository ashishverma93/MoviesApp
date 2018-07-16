package com.ashish.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashish.movies.R;
import com.ashish.movies.repository.model.MovieVideoResponseValue;
import com.ashish.movies.utils.AppConstants;
import com.ashish.movies.view.interfaces.MovieVideoClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieVideoAdapter extends RecyclerView.Adapter<MovieVideoAdapter.MyViewHolder> {
    private Context context;
    private List<MovieVideoResponseValue> castList;
    private final MovieVideoClickListener movieVideoClickListener;

    public MovieVideoAdapter(Context context) {
        this.context = context;
        try {
            this.movieVideoClickListener = ((MovieVideoClickListener) context);
        } catch (Exception e) {
            throw new ClassCastException("Activity must implement MovieListClickInterface");
        }
    }

    public void addMovieVideo(List<MovieVideoResponseValue> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_video, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (castList == null) {
            return 0;
        } else {
            return castList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.imv_movie_video)
        ImageView mImvMovieVideo;

        @BindView(R.id.txv_movie_video_name)
        TextView mTxvVideoName;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(int position) {
            final MovieVideoResponseValue data = castList.get(position);
            if (data.getKey() != null) {
                Glide.with(context)
                        .load(String.format(AppConstants.YOUTUBE_THUMBNAIL, data.getKey()))
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorAccent))
                        .into(mImvMovieVideo);
            }

            if (data.getName() != null) {
                mTxvVideoName.setText(data.getName());
            }
        }

        @OnClick
        void onClick(View view) {
            MovieVideoResponseValue movieVideoResponseValue = castList.get(getAdapterPosition());
            movieVideoClickListener.onVideoItemClickListener(movieVideoResponseValue.getKey());
        }
    }
}