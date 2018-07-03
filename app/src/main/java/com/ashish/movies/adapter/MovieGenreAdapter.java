package com.ashish.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ashish.movies.R;
import com.ashish.movies.db.entity.GenreEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieGenreAdapter extends RecyclerView.Adapter<MovieGenreAdapter.MyViewHolder> {

    private Context context;
    private List<GenreEntity> genreEntities;

    public MovieGenreAdapter(Context context) {
        this.context = context;
        notifyDataSetChanged();
    }

    public void addGenre(List<GenreEntity> genreEntities) {
        this.genreEntities = genreEntities;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_genre, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return genreEntities.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txv_genre)
        TextView mTxvGenreName;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(int position) {
            final GenreEntity data = genreEntities.get(position);

            if (data.getGenreName() != null) {
                mTxvGenreName.setText(data.getGenreName());
            }
        }
    }
}
