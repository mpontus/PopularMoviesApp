package com.mpontus.popularmoviesapp.ui.MovieListFragment;

import com.mpontus.popularmoviesapp.data.AppApiHelper;
import com.mpontus.popularmoviesapp.data.AppConnectivityHelper;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

@FragmentScoped
public class MovieListFragmentPresenter implements MovieListFragmentContract.Presenter {
    private final AppConnectivityHelper mConnectivityHelper;
    private final CompositeDisposable mCompositeDisposable;
    private final Observable<List<Movie>> mMovieList;

    private MovieListFragmentContract.View mView;

    @Inject
    MovieListFragmentPresenter(MovieListFragmentContract.View view,
                               AppApiHelper repository,
                               AppConnectivityHelper networkStateHelper,
                               Observable<List<Movie>> movieList) {
        mView = view;
        mConnectivityHelper = networkStateHelper;
        mCompositeDisposable = new CompositeDisposable();
        mMovieList = movieList;
    }

    public void attach() {
        mCompositeDisposable.add(
                mConnectivityHelper.getIsOnline()
                        .takeUntil(mMovieList)
                        .subscribe(isOnline -> {
                            if (isOnline) {
                                mView.showLoading();
                            } else {
                                mView.showOffline();
                            }
                        })
        );

        mCompositeDisposable.add(
                mMovieList.subscribe(movies -> {
                    mView.setMovieCount(movies.size());
                    mView.showMovies();
                })
        );
    }

    public void detach() {
        mCompositeDisposable.dispose();
    }
}
