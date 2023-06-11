package com.example.finalmobile.repository.callback;

import com.example.finalmobile.models.movie.Movie;

import java.util.List;

public interface OnMovieCallback {
    void onSuccess(int page, List<Movie> movieList);
    void onFailure(String message);
}
