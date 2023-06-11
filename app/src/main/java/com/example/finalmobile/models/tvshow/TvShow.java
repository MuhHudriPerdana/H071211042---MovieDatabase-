package com.example.finalmobile.models.tvshow;

import com.google.gson.annotations.SerializedName;

public class TvShow {
    private int id;
    private String name;

    @SerializedName("poster_path")
    private String imgUrl;

    @SerializedName("vote_average")
    private String voteAverage;

    @SerializedName("first_air_date")
    private String releaseDate;

    public String getVoteAverage() {
        return voteAverage;
    }

    public TvShow() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return name;
    }

    public void setTitle(String name) {
        this.name = name;
    }



    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getReleaseYear() {
        String[] releaseYear = releaseDate.split("-");
        return releaseYear[0];
    }
}
