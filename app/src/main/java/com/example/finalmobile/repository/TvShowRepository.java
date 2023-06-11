package com.example.finalmobile.repository;


import com.example.finalmobile.models.CreditModel;
import com.example.finalmobile.models.DetailModel;
import com.example.finalmobile.models.tvshow.TvShowResponse;
import com.example.finalmobile.networks.Const;
import com.example.finalmobile.networks.TvShowApiInterface;
import com.example.finalmobile.repository.callback.OnCastCallback;
import com.example.finalmobile.repository.callback.OnDetailCallback;
import com.example.finalmobile.repository.callback.OnTvShowCallback;
import com.example.finalmobile.repository.callback.OnTvShowSearchCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TvShowRepository {
    private static TvShowRepository tvRepository;
    private final TvShowApiInterface tvService;

    private TvShowRepository(TvShowApiInterface tvService) {
        this.tvService = tvService;
    }

    public static TvShowRepository getRetrofit() {
        if (tvRepository == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(Const.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            tvRepository = new TvShowRepository(retrofit.create(TvShowApiInterface.class));
        }
        return tvRepository;
    }

    public void getTvShow(String sortBy, int page, final OnTvShowCallback callback) {
        tvService.getResult(sortBy, Const.API_KEY, page).enqueue(new Callback<TvShowResponse>() {
            @Override
            public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                System.out.println("URL :: " + response.raw().request().url());
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getTvShowResult() != null) {
                            callback.onSuccess(response.body().getPage(), response.body().getTvShowResult());
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
            public void onFailure(Call<TvShowResponse> call, Throwable t) {
                callback.onFailure(t.getLocalizedMessage());
            }
        });
    }
    public void getTvShowDetail(int id, final OnDetailCallback callback) {
        tvService.getTvShow(id, Const.API_KEY)
                .enqueue(new Callback<DetailModel>() {
                    @Override
                    public void onResponse(Call<DetailModel> call, Response<DetailModel> response) {
                        System.out.println("URL :: " + response.raw().request().url());
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

    public void getTvShowCast(int id, final OnCastCallback callback) {
        tvService.getTvCast(id, Const.API_KEY).enqueue(new Callback<CreditModel>() {
            @Override
            public void onResponse(Call<CreditModel> call, Response<CreditModel> response) {
                System.out.println("URL :: " + response.raw().request().url());
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
    public void searchTvShow(String query, int page, final OnTvShowSearchCallback callback) {
        tvService.search(Const.API_KEY, query, page)
                .enqueue(new Callback<TvShowResponse>() {
                    @Override
                    public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                if (response.body().getTvShowResult() != null) {
                                    callback.onSuccess(response.body().getTvShowResult(), response.message(), response.body().getPage());
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
                    public void onFailure(Call<TvShowResponse> call, Throwable t) {
                        callback.onFailure(t.getLocalizedMessage());
                    }
                });
    }
}

