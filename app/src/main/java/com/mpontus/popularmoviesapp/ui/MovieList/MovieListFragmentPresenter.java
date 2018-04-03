package com.mpontus.popularmoviesapp.ui.MovieList;

import com.mpontus.popularmoviesapp.data.AppApiHelper;
import com.mpontus.popularmoviesapp.data.AppConnectivityHelper;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@FragmentScoped
public class MovieListFragmentPresenter implements MovieListFragmentContract.Presenter {
    private final AppApiHelper mApiHelper;
    private final AppConnectivityHelper mConnectivityHelper;
    private final Scheduler mMainThreadScheduler;
    private final CompositeDisposable mCompositeDisposable;

    private MovieListFragmentContract.View mView;

    private boolean mOnline;
    private TMDbService.MovieSource mMovieSource;
    private boolean mLoadWhenOnline = true;
    private boolean mRequestPending = false;

    @Inject
    MovieListFragmentPresenter(MovieListFragmentContract.View view,
                               AppApiHelper repository,
                               AppConnectivityHelper networkStateHelper,
                               @Named("MAIN") Scheduler mainThreadScheduler,
                               TMDbService.MovieSource movieSource) {
        mView = view;
        mApiHelper = repository;
        mConnectivityHelper = networkStateHelper;
        mMainThreadScheduler = mainThreadScheduler;
        mCompositeDisposable = new CompositeDisposable();
        mMovieSource = movieSource;
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

                    mView.setMovieCount(movies.size());

                    mView.showMovies();
                });
    }
}