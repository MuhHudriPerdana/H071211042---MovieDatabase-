package com.example.finalmobile.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailModel {
    @SerializedName("number_of_episodes")
    private int eps;

    private String status;
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("poster_path")
    @Expose
    private String poster;

    @SerializedName("backdrop_path")
    @Expose
    private String backdrop;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("vote_average")
    @Expose
    private String voteAverage;

    @SerializedName("runtime")
    @Expose
    private String duration;

    private String name;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public String getOverview() {
        return overview;
    }
    public String getVoteAverage() {
        return voteAverage;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getEps() {
        String episode = eps +" eps";
        return episode;
    }
    public String getPoster() {
        return poster;
    }
    public String getBackdrop() {
        return backdrop;
    }
    public String getDuration() {
        return duration+" min";
    }
    public String getTitle() {
        String title = this.title != null ? this.title:this.name;
        return title;
    }
    public Float getRating() {
        Float rating = ((Float.parseFloat(voteAverage))/10)*5;
        return rating;
    }
    public String getYear() {
        String[] relaseYear = releaseDate.split("-");
        return relaseYear[0];
    }
    public List<Genre> getGenres() {
        return genres;
    }
    public String getStatus() {
        return status;
    }
}
