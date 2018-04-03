package com.mpontus.popularmoviesapp.ui.MovieListItem;

import com.mpontus.popularmoviesapp.data.Navigator;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

// Using assited injection to provide dependencies to the presnter
@FragmentScoped
public class MovieListItemPresenterFactory {
    private Navigator mNavigator;
    private CompositeDisposable mCompositeDisposable;
    private Observable<List<Movie>> mMovieList;

    @Inject
    public MovieListItemPresenterFactory(Navigator navigator,
                                         CompositeDisposable compositeDisposable,
                                         Observable<List<Movie>> movieList) {
        mNavigator = navigator;
        mCompositeDisposable = compositeDisposable;
        mMovieList = movieList;
    }

    public MovieListItemContract.Presenter createPresenter(MovieListItemContract.View view, int position) {
        return new MovieListItemPresenter(mNavigator, mCompositeDisposable, mMovieList, view, position);
    }
}
