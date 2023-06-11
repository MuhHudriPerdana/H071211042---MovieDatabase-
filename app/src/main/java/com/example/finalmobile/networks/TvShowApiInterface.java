package com.example.finalmobile.networks;



import com.example.finalmobile.models.CreditModel;
import com.example.finalmobile.models.DetailModel;
import com.example.finalmobile.models.tvshow.TvShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TvShowApiInterface {
        @GET("tv/{sort_by}")
        Call<TvShowResponse> getResult(
                @Path("sort_by") String sortBy,
                @Query("api_key") String apiKey,
                @Query("page") int page
        );

        @GET("tv/{tv_id}")
        Call<DetailModel> getTvShow(
                @Path("tv_id") int id,
                @Query("api_key") String apiKey
        );

        @GET("search/tv")
        Call<TvShowResponse> search(
                @Query("api_key") String apiKey,
                @Query("query") String query,
                @Query("page") int page
        );

        @GET("tv/{tv_id}/credits")
        Call<CreditModel> getTvCast (
                @Path("tv_id") int id,
                @Query("api_key") String apiKey
        );

}
