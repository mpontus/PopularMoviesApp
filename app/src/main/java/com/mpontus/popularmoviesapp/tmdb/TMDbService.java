package com.mpontus.popularmoviesapp.tmdb;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface TMDbService {

    enum MovieSource {
        POPULAR(1), TOP_RATED(2);

        private int value;

        MovieSource(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static MovieSource fromValue(int v) {
            switch (v) {
                case 1:
                    return POPULAR;
                case 2:
                    return TOP_RATED;
                default:
                    return POPULAR;
            }
        }
    }

    @GET("movie/popular")
    Observable<MovieListResponse> getPopularMovies();

    @GET("movie/top_rated")
    Observable<MovieListResponse> getTopRatedMovies();
}
