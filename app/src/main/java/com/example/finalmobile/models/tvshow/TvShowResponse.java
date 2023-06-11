package com.example.finalmobile.models.tvshow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShowResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("total_results")
    private int totalResluts;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<TvShow> tvShowResult;

    public List<TvShow> getTvShowResult() {
        return tvShowResult;
    }

    public int getPage() {
        return page;
    }

    public int getTotalResluts() {
        return totalResluts;
    }

    public int getTotalPages() {
        return totalPages;
    }

}
