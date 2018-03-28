package com.mpontus.popularmoviesapp.data.network;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.MovieListResponse;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

class AppApiHelper implements ApiHelper {
    private final TMDbService mApi;

    @Inject
    AppApiHelper(TMDbService api) {
        mApi = api;
    }

    @Override
    public Observable<List<Movie>> getMovies(TMDbService.MovieSource source) {
        return getResponse(source).map(response -> response.results);
    }

    private Observable<MovieListResponse> getResponse(TMDbService.MovieSource source) {
        switch (source) {
            case POPULAR:
                return mApi.getPopularMovies();

            case TOP_RATED:
                return mApi.getTopRatedMovies();

            default:
                throw new RuntimeException("Unsupported source: " + source.toString());
        }
    }
}
