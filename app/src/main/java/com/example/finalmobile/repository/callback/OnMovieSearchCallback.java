package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.movie.Movie;

import java.util.List;

public interface OnMovieSearchCallback {
    void onSuccess(List<Movie> movies, String msg, int page);
    void onFailure(String msg);
}
