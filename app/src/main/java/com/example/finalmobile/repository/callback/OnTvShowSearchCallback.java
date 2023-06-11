package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.tvshow.TvShow;

import java.util.List;

public interface OnTvShowSearchCallback {
    void onSuccess(List<TvShow> movies, String msg, int page);
    void onFailure(String msg);
}
