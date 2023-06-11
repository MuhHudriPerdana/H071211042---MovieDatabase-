package com.example.finalmobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.widget.SearchView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.finalmobile.adapters.MovieAdapter;
import com.example.finalmobile.adapters.onclick.OnItemClickListener;
import com.example.finalmobile.local.table.FavoriteMovie;
import com.example.finalmobile.local.table.FavoriteTv;
import com.example.finalmobile.models.movie.Movie;
import com.example.finalmobile.models.tvshow.TvShow;
import com.example.finalmobile.repository.MovieRepository;
import com.example.finalmobile.repository.callback.OnMovieCallback;
import com.example.finalmobile.repository.callback.OnMovieSearchCallback;

import java.util.List;

public class MovieFragment extends Fragment implements OnItemClickListener, SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener{
private SwipeRefreshLayout refreshLayout;
private ProgressBar tvProgressBar;
private LinearLayout llNoRecord;

private static final String TAG = "movie";
private static final String[] SORT_BY_LIST = {"now_playing","popular","top_rated","upcoming"};

private RecyclerView recyclerView;
private MovieRepository repository;
private MovieAdapter adapter;

private int currentPage = 1;
private boolean isFetching;
private String sortBy;

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_movie, container, false);

        refreshLayout = v.findViewById(R.id.swl_movie);
        recyclerView = v.findViewById(R.id.rv_movie);
        tvProgressBar = v.findViewById(R.id.pb_movie);
        llNoRecord = v.findViewById(R.id.ll_movie_empty);
        repository = MovieRepository.getRetrofit();

        setSortBy(SORT_BY_LIST[1]);

final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
@Override
public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        int totalItem = layoutManager.getItemCount();
        int visibleItem = layoutManager.getChildCount();
        int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
        if (firstVisibleItem + visibleItem >= totalItem / 2) {
        if (!isFetching) {
        isFetching = true;
        currentPage++;
        loadData("", currentPage);
        isFetching = false;
        }
        }
        }
        });

        refreshLayout.setOnRefreshListener(this);


        //load all movie
        loadData("", currentPage);
        return v;
        }

private void setSortBy(String sortBy) {
        this.sortBy = sortBy;
        }


@Override
public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        }


@Override
public boolean onQueryTextSubmit(String query) {
        return false;
        }
@Override
public boolean onQueryTextChange(String newText) {

        if (newText.length() > 0) {
        adapter = null;
        tvProgressBar.setVisibility(View.VISIBLE);
        loadData(newText, currentPage);

        } else {
        adapter = null;
        tvProgressBar.setVisibility(View.VISIBLE);
        loadData("", currentPage);
        }
        return true;
        }

private void loadData(String query, int page) {
        isFetching = true;
        if(query.equals("")){
        repository.getMovie(sortBy, page, new OnMovieCallback() {
@Override
public void onSuccess(int page, List<Movie> movieList) {
        //if adapter actualy null
        if(adapter == null){
        adapter = new MovieAdapter(movieList);
        adapter.setClickListener(MovieFragment.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        llNoRecord.setVisibility(View.GONE);
        }
        else{
        adapter.appendList(movieList);
        }

        tvProgressBar.setVisibility(View.GONE);
        currentPage = page;
        isFetching = false;
        refreshLayout.setRefreshing(false);
        }
@Override
public void onFailure(String message) {
        tvProgressBar.setVisibility(View.GONE);
        llNoRecord.setVisibility(View.VISIBLE);
        Log.d(TAG, "onFailure: " + message);
        Toast.makeText(getActivity(), "Failed " + message, Toast.LENGTH_LONG).show();
        }
        });
        }
        else{
        repository.searchMovie(query, page, new OnMovieSearchCallback() {
@Override
public void onSuccess(List<Movie> movies, String msg, int page) {
        if(adapter == null){
        adapter = new MovieAdapter(movies);
        adapter.setClickListener(MovieFragment.this);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
        }else{
        //if adapter has been created, appeng movie list
        adapter.appendList(movies);
        }
        currentPage = page;
        isFetching = false;
        refreshLayout.setRefreshing(false);
        }
@Override
public void onFailure(String msg) {
        tvProgressBar.setVisibility(View.VISIBLE);
        }
        });
        }
        }

@Override
public void onRefresh() {
        adapter = null;
        currentPage =1;
        tvProgressBar.setVisibility(View.VISIBLE);
        loadData("", currentPage);
        }


@Override
public void onItemClick(Movie movie) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", movie.getId());
        detailActivity.putExtra("TYPE", TAG);
        startActivity(detailActivity);
        }

@Override
public void onItemClick(FavoriteMovie favoriteMovie) {}
@Override
public void onItemClick(FavoriteTv favoriteTv) {}
@Override
public void onItemClick(TvShow tv) {}
        }