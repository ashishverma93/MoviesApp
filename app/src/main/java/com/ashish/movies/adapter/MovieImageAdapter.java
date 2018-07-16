package com.ashish.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashish.movies.R;
import com.ashish.movies.repository.model.MovieImageBackdropResponseValue;
import com.ashish.movies.utils.AppConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.MyViewHolder> {

    private Context context;
    private List<MovieImageBackdropResponseValue> castList;

    public MovieImageAdapter(Context context) {
        this.context = context;
    }

    public void addMovieImages(List<MovieImageBackdropResponseValue> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_image, parent, false));
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
        @BindView(R.id.imv_movie_images_backdrop)
        ImageView mImvMovieBackdropImage;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(int position) {
            final MovieImageBackdropResponseValue data = castList.get(position);
            if (data.getFilePath() != null) {
                Glide.with(context)
                        .load(AppConstants.BACKDROP_BASE_PATH + data.getFilePath())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorAccent))
                        .into(mImvMovieBackdropImage);
            }

        }

        @OnClick
        void onClick(View view) {

        }
    }
}
