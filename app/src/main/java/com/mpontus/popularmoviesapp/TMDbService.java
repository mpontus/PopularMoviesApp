package com.mpontus.popularmoviesapp;

import io.reactivex.Observable;
import retrofit2.http.GET;

interface TMDbService {
    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieListResponse> getTopRatedMovies();
}
