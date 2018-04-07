package com.mpontus.popularmoviesapp.ui.MovieListFragment;

import android.util.Log;

import com.mpontus.popularmoviesapp.data.AppConnectivityHelper;
import com.mpontus.popularmoviesapp.data.MovieRepository;
import com.mpontus.popularmoviesapp.data.Navigator;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.ui.MovieListItem.MovieListItemViewHolder;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.disposables.CompositeDisposable;

@FragmentScoped
public class MovieListPresenter implements MovieListFragmentContract.Presenter {
    private static final String TAG = "MovieListPresenter";

    private final MovieListFragmentContract.View mView;
    private final AppConnectivityHelper mConnectivityHelper;
    private final Navigator mNavigator;
    private final MovieRepository mRepository;
    private final Scheduler mMainThreadScheduler;
    private final Scheduler mBackgroundThreadScheduler;
    private final MovieSourceType mMovieSourceType;
    private final CompositeDisposable mCompositeDisposable;
    private List<Movie> mMovieList;

    @Inject
    MovieListPresenter(AppConnectivityHelper networkStateHelper,
                       Navigator navigator,
                       MovieRepository repository,
                       @Named("MAIN") Scheduler mainThreadScheduler,
                       @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                       MovieListFragment view,
                       MovieSourceType movieSourceType) {
        mConnectivityHelper = networkStateHelper;
        mNavigator = navigator;
        mRepository = repository;
        mMainThreadScheduler = mainThreadScheduler;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mView = view;
        mMovieSourceType = movieSourceType;
        mCompositeDisposable = new CompositeDisposable();
    }

    public void attach() {
        Observable<Boolean> isOnlineObservable = mConnectivityHelper.getIsOnline();
        Observable<List<Movie>> movieListObservable = isOnlineObservable
                .switchMap(isOnline -> {
                    if (!isOnline) {
                        return Observable.empty();
                    }

                    Observable<List<Movie>> resultsObservable;

                    if (mMovieSourceType == MovieSourceType.POPULAR) {
                        resultsObservable = mRepository.getPopularMovies();
                    } else {
                        resultsObservable = mRepository.getTopRatedMovies();
                    }

                    return resultsObservable.subscribeOn(mBackgroundThreadScheduler);
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

    public ItemPresenter createItemPresenter(MovieListItemViewHolder view, int position) {
        return new ItemPresenter(view, position);
    }

    public class ItemPresenter {
        private final MovieListItemViewHolder mView;
        private final Movie mMovie;

        ItemPresenter(MovieListItemViewHolder view, int position) {
            mView = view;
            mMovie = mMovieList.get(position);
        }

        public void attach() {
            mView.setTitle(mMovie.title);
            mView.setBackdrop(mMovie.backdropPath);
        }

        public void detach() {
        }

        public void onClick() {
            mNavigator.openMovieDetails(mMovie);
        }
    }
}
