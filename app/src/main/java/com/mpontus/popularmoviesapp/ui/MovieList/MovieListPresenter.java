package com.mpontus.popularmoviesapp.ui.MovieList;

import com.mpontus.popularmoviesapp.data.connectivity.AppConnetivityHelper;
import com.mpontus.popularmoviesapp.data.connectivity.ConnectivityHelper;
import com.mpontus.popularmoviesapp.data.network.ApiHelper;
import com.mpontus.popularmoviesapp.data.network.AppApiHelper;
import com.mpontus.popularmoviesapp.data.preferences.AppPreferencesHelper;
import com.mpontus.popularmoviesapp.data.preferences.PreferencesHelper;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@ActivityScoped
public class MovieListPresenter implements MovieListContract.Presenter {
    private final ApiHelper mApiHelper;
    private final ConnectivityHelper mConnectivityHelper;
    private final PreferencesHelper mPreferencesHelper;
    private final Scheduler mMainThreadScheduler;
    private final CompositeDisposable mCompositeDisposable;

    private MovieListContract.View mView;

    private boolean mOnline;
    private TMDbService.MovieSource mMovieSource;
    private boolean mLoadWhenOnline;
    private boolean mRequestPending;

    @Inject
    MovieListPresenter(MovieListContract.View view,
                       AppApiHelper repository,
                       AppConnetivityHelper networkStateHelper,
                       AppPreferencesHelper preferencesHelper,
                       @Named("MAIN") Scheduler mainThreadScheduler) {
        mView = view;
        mApiHelper = repository;
        mConnectivityHelper = networkStateHelper;
        mPreferencesHelper = preferencesHelper;
        mMainThreadScheduler = mainThreadScheduler;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void attach() {
        mCompositeDisposable.add(
                mConnectivityHelper.getIsOnline().subscribe(isOnline -> {
                    mOnline = isOnline;

                    if (isOnline) {
                        if (mLoadWhenOnline) {
                            load(mMovieSource);
                        }
                    } else {
                        if (mRequestPending) {
                            mView.showOffline();
                        }
                    }
                })
        );

        mCompositeDisposable.add(
                mPreferencesHelper.getMovieSource().subscribe(movieSource -> {
                    mMovieSource = movieSource;

                    this.load(movieSource);
                })
        );
    }

    public void detach() {
        mCompositeDisposable.dispose();
    }

    private void load(TMDbService.MovieSource source) {
        if (!mOnline) {
            // Set the flag which will start loading when we come back online.
            mLoadWhenOnline = true;

            mView.showOffline();

            return;
        }

        // Set the flag which will show offline screen if network goes offline during loading.
        mRequestPending = true;

        mView.showLoading();

        mApiHelper.getMovies(source)
                .observeOn(mMainThreadScheduler)
                .subscribe(movies -> {
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
