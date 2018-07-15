package com.ashish.movies.repository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieImageResponse {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("backdrops")
    @Expose
    private List<MovieImageBackdropResponseValue> backdrops = null;
    @SerializedName("posters")
    @Expose
    private List<MovieImagePosterResponseValue> posters = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieImageBackdropResponseValue> getBackdrops() {
        return backdrops;
    }

    public void setBackdrops(List<MovieImageBackdropResponseValue> backdrops) {
        this.backdrops = backdrops;
    }

    public List<MovieImagePosterResponseValue> getPosters() {
        return posters;
    }

    public void setPosters(List<MovieImagePosterResponseValue> posters) {
        this.posters = posters;
    }
}
