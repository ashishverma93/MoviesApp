package com.ashish.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ashish.movies.R;
import com.ashish.movies.repository.model.MovieImageResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieImageAdapter extends RecyclerView.Adapter<MovieImageAdapter.MyViewHolder> {

    private Context context;
    private List<MovieImageResponse> castList;

    public MovieImageAdapter(Context context) {
        this.context = context;
    }

    public void addMovieImages(List<MovieImageResponse> castList) {
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
            final MovieImageResponse data = castList.get(position);

        }

        @OnClick
        void onClick(View view) {

        }
    }
}
