package com.ashish.movies.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.ashish.movies.utils.AppConstants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = AppConstants.TABLE_GENRE)
public class GenreEntity {
    @NonNull
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private Integer genreId;
    @SerializedName("name")
    @Expose
    private String genreName;

    public GenreEntity(@NonNull Integer genreId, String genreName) {
        this.genreId = genreId;
        this.genreName = genreName;
    }

    @NonNull
    public Integer getGenreId() {
        return genreId;
    }

    public String getGenreName() {
        return genreName;
    }
}
