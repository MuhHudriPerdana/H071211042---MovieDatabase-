package com.example.finalmobile.adapters.onclick;

import com.example.finalmobile.local.table.FavoriteMovie;
import com.example.finalmobile.local.table.FavoriteTv;
import com.example.finalmobile.models.movie.Movie;
import com.example.finalmobile.models.tvshow.TvShow;

public interface OnItemClickListener {
    void onItemClick(FavoriteMovie favoriteMovie);
    void onItemClick(FavoriteTv favoriteTv);
    void onItemClick(Movie movie);
    void onItemClick(TvShow tv);
}
