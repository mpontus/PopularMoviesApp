package com.mpontus.popularmoviesapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbService {
    @GET("movie/popular")
    Call<MovieListResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );
}
