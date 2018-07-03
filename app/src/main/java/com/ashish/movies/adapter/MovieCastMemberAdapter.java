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
import com.ashish.movies.repository.model.MovieCastMemberResponseValue;
import com.ashish.movies.utils.AppConstants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieCastMemberAdapter extends RecyclerView.Adapter<MovieCastMemberAdapter.MyViewHolder> {

    private Context context;
    private List<MovieCastMemberResponseValue> castList;

    public MovieCastMemberAdapter(Context context) {
        this.context = context;
    }

    public void addMovieCastMember(List<MovieCastMemberResponseValue> castList) {
        this.castList = castList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie_cast_member, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txv_member_cast_name)
        TextView mTxvCastMemberName;
        @BindView(R.id.txv_member_cast_role)
        TextView mTxvCastMemberRole;
        @BindView(R.id.imv_member_cast)
        ImageView mImvCastMember;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void onBind(int position) {
            final MovieCastMemberResponseValue data = castList.get(position);
            if (data.getProfilePath() != null) {
                Glide.with(context)
                        .load(AppConstants.POSTER_BASE_PATH + data.getProfilePath())
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                                .placeholder(R.color.colorAccent)
                                .error(R.color.colorAccent))
                        .into(mImvCastMember);
            }

            if (data.getName() != null) {
                mTxvCastMemberName.setText(data.getName());
            }

            if (data.getCharacter() != null) {
                mTxvCastMemberRole.setText(data.getCharacter());
            }
        }
    }
}
