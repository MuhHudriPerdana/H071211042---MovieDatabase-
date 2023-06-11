package com.example.finalmobile.repository;


import com.example.finalmobile.models.CreditModel;
import com.example.finalmobile.models.DetailModel;
import com.example.finalmobile.models.movie.MovieResponse;
import com.example.finalmobile.networks.Const;
import com.example.finalmobile.networks.MovieApiInterface;
import com.example.finalmobile.repository.callback.OnCastCallback;
import com.example.finalmobile.repository.callback.OnDetailCallback;
import com.example.finalmobile.repository.callback.OnMovieCallback;
import com.example.finalmobile.repository.callback.OnMovieSearchCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieRepository {
    //attribute
    private static MovieRepository movieRepository;
    private final MovieApiInterface movieService;

    //container
    private MovieRepository(MovieApiInterface movieService){
        this.movieService = movieService;
    }

    //instance
    public static MovieRepository getRetrofit() {
        if(movieRepository==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            movieRepository = new MovieRepository(retrofit.create(MovieApiInterface.class));
        }
        return movieRepository;
    }

    //get movie
    public void getMovie(String sortBy, int page, final OnMovieCallback callback){
        movieService.getResult(sortBy, Const.API_KEY, page).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getMovieResult()!= null) {
                            callback.onSuccess(response.body().getPage(), response.body().getMovieResult());
                        } else {
                            callback.onFailure("response.body().getResults() is null");
                        }
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message());
                }
            }
            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }

    //get movie detail
    public void getMovieDetail(int id, final OnDetailCallback callback) {
        movieService.getMovie(id, Const.API_KEY)
                .enqueue(new Callback<DetailModel>() {
                    @Override
                    public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                        System.out.println("URL :: "+ response.raw().request().url());
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                callback.onSuccess(response.body(), response.message());
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + ", Error Code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailModel> call, Throwable t) {

                    }
                });
    }
    public void getMovieCast(int id, final OnCastCallback callback){
        movieService.getMovieCast(id, Const.API_KEY).enqueue(new Callback<CreditModel>() {
            @Override
            public void onResponse(Call<CreditModel> call, Response<CreditModel> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        callback.onSuccess(response.body(), response.message());
                    } else {
                        callback.onFailure("response.body() is null");
                    }
                } else {
                    callback.onFailure(response.message() + ", Error Code : " + response.code());
                }
            }
            @Override
            public void onFailure(Call<CreditModel> call, Throwable t) {

            }
        });
    }

    //get search result
    public void searchMovie(String query, int page, final OnMovieSearchCallback callback) {
        movieService.search(Const.API_KEY, query, page)
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        System.out.println("URL :: "+ response.raw().request().url());
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getMovieResult() != null) {
                                    callback.onSuccess(response.body().getMovieResult(), response.message(), response.body().getPage());
                                } else {
                                    callback.onFailure("No Results");
                                }
                            } else {
                                callback.onFailure("response.body() is null");
                            }
                        } else {
                            callback.onFailure(response.message() + " : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }

}
