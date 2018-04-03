package com.mpontus.popularmoviesapp.ui.MovieListItem;

import com.mpontus.popularmoviesapp.data.Navigator;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;

public class MovieListItemPresenter implements MovieListItemContract.Presenter {
    private Navigator mNavigator;
    private CompositeDisposable mCompositeDisposable;
    private MovieListItemContract.View mView;
    private Observable<List<Movie>> mMovieList;
    private int mPosition;

    MovieListItemPresenter(Navigator navigator,
                           CompositeDisposable compositeDisposable,
                           Observable<List<Movie>> movieList,
                           MovieListItemContract.View view,
                           int position) {
        mNavigator = navigator;
        mCompositeDisposable = compositeDisposable;
        mMovieList = movieList;
        mView = view;
        mPosition = position;
    }

    public void attach() {
        mCompositeDisposable.add(
                getMovieAtPosition(mPosition).subscribe(movie -> {
                    mView.setPoster(movie.getPosterUrl(Movie.POSTER_SIZE_W185));
                })
        );
    }

    public void detach() {
        mCompositeDisposable.dispose();
    }

    @Override
    public void onClick() {
        mCompositeDisposable.add(
                getMovieAtPosition(mPosition).subscribe(movie -> {
                    mNavigator.openMovieDetails(movie);
                })
        );
    }

    private Observable<Movie> getMovieAtPosition(int position) {
        return mMovieList.map(movies -> movies.get(mPosition));
    }
}
