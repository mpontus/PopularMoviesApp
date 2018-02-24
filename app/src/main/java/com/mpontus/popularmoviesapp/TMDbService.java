package com.mpontus.popularmoviesapp;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface TMDbService {
    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies(
            @Query("api_key") String apiKey
    );
}
