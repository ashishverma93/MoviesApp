package com.ashish.movies.repository.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieCastAndCrewMemberResponse {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<MovieCastMemberResponseValue> cast = null;
    @SerializedName("crew")
    @Expose
    private List<MovieCrewMemberResponseValue> crew = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<MovieCastMemberResponseValue> getCast() {
        return cast;
    }

    public void setCast(List<MovieCastMemberResponseValue> cast) {
        this.cast = cast;
    }

    public List<MovieCrewMemberResponseValue> getCrew() {
        return crew;
    }

    public void setCrew(List<MovieCrewMemberResponseValue> crew) {
        this.crew = crew;
    }
}
