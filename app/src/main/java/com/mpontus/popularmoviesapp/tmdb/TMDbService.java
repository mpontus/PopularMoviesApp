package com.mpontus.popularmoviesapp.tmdb;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TMDbService {
    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieListResponse> getTopRatedMovies();
}
