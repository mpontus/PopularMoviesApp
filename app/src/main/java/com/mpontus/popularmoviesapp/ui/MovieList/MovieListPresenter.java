package com.mpontus.popularmoviesapp.ui.MovieList;

import android.util.Log;
import android.view.View;

import com.mpontus.popularmoviesapp.data.MovieRepository;
import com.mpontus.popularmoviesapp.data.Navigator;
import com.mpontus.popularmoviesapp.data.device.ConnectivityManager;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@FragmentScoped
public class MovieListPresenter {
    private static final String TAG = "MovieListPresenter";

    private final MovieListFragment mView;
    private final ConnectivityManager mConnectivityManager;
    private final Navigator mNavigator;
    private final MovieRepository mRepository;
    private final Scheduler mMainThreadScheduler;
    private final Scheduler mBackgroundThreadScheduler;
    private final MovieSourceType mMovieSourceType;
    private final CompositeDisposable mCompositeDisposable;
    private List<Movie> mMovieList;

    @Inject
    MovieListPresenter(ConnectivityManager connectivityManager,
                       Navigator navigator,
                       MovieRepository repository,
                       @Named("MAIN") Scheduler mainThreadScheduler,
                       @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                       MovieListFragment view,
                       MovieSourceType movieSourceType) {
        mConnectivityManager = connectivityManager;
        mNavigator = navigator;
        mRepository = repository;
        mMainThreadScheduler = mainThreadScheduler;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mView = view;
        mMovieSourceType = movieSourceType;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void attach() {
        Observable<Boolean> isOnlineObservable = mConnectivityManager.getIsOnline();
        Observable<List<Movie>> movieListObservable = isOnlineObservable
                .switchMap(isOnline -> {
                    if (!isOnline) {
                        return Observable.empty();
                    }

                    return getMovieListObservable()
                            .subscribeOn(mBackgroundThreadScheduler);
                })
                .share();

        mCompositeDisposable.add(
                isOnlineObservable
                        .takeUntil(movieListObservable)
                        .observeOn(mMainThreadScheduler)
                        .subscribe(isOnline -> {
                            if (isOnline) {
                                mView.showLoading();
                            } else {
                                mView.showOffline();
                            }
                        }, error -> {
                            Log.e(TAG, error.getMessage());
                        })
        );

        mCompositeDisposable.add(
                movieListObservable
                        .observeOn(mMainThreadScheduler)
                        .subscribe(movies -> {
                            mMovieList = movies;

                            mView.setMovieCount(movies.size());

                            mView.showMovies();
                        }, error -> {
                            Log.e(TAG, error.getMessage());
                        })
        );
    }

    public void detach() {
        mCompositeDisposable.dispose();
    }

    public ItemPresenter createItemPresenter(MovieListViewHolder view, int position) {
        return new ItemPresenter(view, position);
    }

    private Observable<List<Movie>> getMovieListObservable() {
        switch (mMovieSourceType) {
            case POPULAR:
                return mRepository.getPopularMovies();

            case TOP_RATED:
                return mRepository.getTopRatedMovies();

            case FAVORITE:
                return mRepository.getFavoriteMovies();

            default:
                return Observable.empty();
        }
    }

    public class ItemPresenter {
        private final MovieListViewHolder mView;
        private final Movie mMovie;

        ItemPresenter(MovieListViewHolder view, int position) {
            mView = view;
            mMovie = mMovieList.get(position);
        }

        public void attach() {
            mView.setTitle(mMovie.title);
            mView.setBackdrop(mMovie.backdropPath);
            mView.setPoster(mMovie.posterPath);
        }

        public void detach() {
        }

        public void onClick(View view, View backdropView, View posterView) {
            mNavigator.openMovieDetails(mMovie, null, posterView);
        }
    }
}
