package com.mpontus.popularmoviesapp.data;

import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.MovieListResponse;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;

@ActivityScoped
public class AppApiHelper {
    private final TMDbService mApi;
    private final Scheduler mBackgroundThreadScheduler;
    private final Scheduler mMainThreadScheduler;

    @Inject
    AppApiHelper(TMDbService api,
                 @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                 @Named("MAIN") Scheduler mainThreadScheduler) {
        mApi = api;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mMainThreadScheduler = mainThreadScheduler;
    }

    public Observable<List<Movie>> getMovies(TMDbService.MovieSource source) {
        return getResponse(source).map(response -> response.results)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler);
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
