package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.tvshow.TvShow;

import java.util.List;

public interface OnTvShowCallback {
    void onSuccess(int page, List<TvShow> tvList);
    void onFailure(String message);
}
