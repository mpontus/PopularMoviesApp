package com.mpontus.popularmoviesapp.ui.MovieList;

import com.mpontus.popularmoviesapp.data.connectivity.ConnectivityHelper;
import com.mpontus.popularmoviesapp.data.network.ApiHelper;
import com.mpontus.popularmoviesapp.data.preferences.PreferencesHelper;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

public class MovieListPresenter implements MovieListContract.Presenter {
    private final ApiHelper mApiHelper;
    private final ConnectivityHelper mConnectivityHelper;
    private final PreferencesHelper mPreferencesHelper;

    private MovieListContract.View mView;

    private boolean mLoadWhenOnline;
    private boolean mRequestPending;

    public MovieListPresenter(ApiHelper repository,
                              ConnectivityHelper networkStateHelper,
                              PreferencesHelper preferencesHelper) {
        this.mApiHelper = repository;
        this.mConnectivityHelper = networkStateHelper;
        this.mPreferencesHelper = preferencesHelper;
    }

    public void attach(MovieListContract.View view) {
        mConnectivityHelper.onOnlineStatusChange(isOnline -> {
            if (isOnline) {
                if (mLoadWhenOnline) {
                    load();
                }
            } else {
                if (mRequestPending) {
                    mView.showOffline();
                }
            }
        });

        mPreferencesHelper.onMovieSourceChange(this::load);

        load();
    }

    private void load() {
        load(mPreferencesHelper.getMovieSource());
    }

    private void load(TMDbService.MovieSource source) {
        if (!mConnectivityHelper.isOnline()) {
            // Set the flag which will start loading when we come back online.
            mLoadWhenOnline = true;

            mView.showOffline();

            return;
        }

        // Set the flag which will show offline screen if network goes offline during loading.
        mRequestPending = true;

        mView.showLoading();

        mApiHelper.getMovies(source).subscribe(movies -> {
            mRequestPending = false;
            mLoadWhenOnline = false;

            mView.setMovies(movies);

            mView.showMovies();
        });
    }

    public void onMovieSourceChange(TMDbService.MovieSource source) {
        mPreferencesHelper.setMovieSource(source);
    }

    public void onMovieClick(Movie movie) {
        mView.openDetailActivity(movie);
    }
}
