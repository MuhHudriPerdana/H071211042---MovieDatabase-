package com.example.finalmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


import com.example.finalmobile.adapters.FavouriteMovieAdapter;
import com.example.finalmobile.adapters.onclick.OnItemClickListener;
import com.example.finalmobile.local.RoomHelper;
import com.example.finalmobile.local.table.FavoriteMovie;
import com.example.finalmobile.local.table.FavoriteTv;
import com.example.finalmobile.models.movie.Movie;
import com.example.finalmobile.models.tvshow.TvShow;

import java.util.List;


public class FavoriteMovieFragment extends Fragment implements OnItemClickListener {
    private RecyclerView recyclerView;
    private LinearLayout llNoRecord;

    private static final String TAG = "movie";
    private RoomHelper roomHelper;
    private List<FavoriteMovie> favoriteMovieList;
    private FavouriteMovieAdapter favMovieAdapter;

    public FavoriteMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_page, container, false);

        llNoRecord = view.findViewById(R.id.ll_fav_empty);
        llNoRecord.setVisibility(View.GONE);
        recyclerView = view.findViewById(R.id.rv_favorite);

        roomHelper = new RoomHelper(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        favoriteMovieList = roomHelper.readFavMovie();
        favMovieAdapter = new FavouriteMovieAdapter(favoriteMovieList);
        favMovieAdapter.setClickListener(this);
        recyclerView.setAdapter(favMovieAdapter);
        if(favoriteMovieList.size()==0){
            llNoRecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(FavoriteMovie favoriteMovie) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", favoriteMovie.getId());
        detailActivity.putExtra("TYPE", TAG);
        startActivity(detailActivity);
    }

    @Override
    public void onItemClick(FavoriteTv favoriteTv) {

    }

    @Override
    public void onItemClick(Movie movie) {

    }

    @Override
    public void onItemClick(TvShow tv) {

    }
}