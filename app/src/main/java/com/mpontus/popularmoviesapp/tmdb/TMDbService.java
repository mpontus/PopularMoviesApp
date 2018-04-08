package com.mpontus.popularmoviesapp.tmdb;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TMDbService {
    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieListResponse> getTopRatedMovies();

    @GET("movie/{movieId}/reviews")
    Observable<ReviewListResponse> getMovieReviews(@Path("movieId") String movieId);

    @GET("movie/{movieId}/videos")
    Observable<VideoListResponse> getMovieVideos(@Path("movieId") String movieId);
}
