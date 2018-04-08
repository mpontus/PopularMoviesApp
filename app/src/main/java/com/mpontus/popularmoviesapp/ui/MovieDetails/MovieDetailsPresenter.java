package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.util.Log;

import com.mpontus.popularmoviesapp.data.MovieRepository;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

@ActivityScoped
public class MovieDetailsPresenter {

    private final MovieRepository mRepository;
    private final Scheduler mMainThreadScheduler;
    private final Scheduler mBackgroundThreadScheduler;
    private MovieDetailsActivity mView;
    private Movie mMovie;

    @Inject
    MovieDetailsPresenter(MovieRepository repository,
                          @Named("MAIN") Scheduler mainThreadScheduler,
                          @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                          MovieDetailsActivity view,
                          Movie movie) {
        mRepository = repository;
        mMainThreadScheduler = mainThreadScheduler;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mView = view;
        mMovie = movie;
    }

    public void attach() {
        mView.setTitle(mMovie.title);
        mView.setBackdrop(mMovie.getBackdropUrl());
        mView.setDescription(mMovie.overview);

        mRepository.isMovieFavorite(mMovie)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(isFavorite -> {
                    if (isFavorite) {
                        mView.showUnfavoriteButton();
                    } else {
                        mView.showFavoriteButton();
                    }
                });

        mRepository.getMovieReviews(mMovie)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(reviews -> {
                    Log.v("Reviews", "" + reviews.size());
                });

        mRepository.getMovieVideos(mMovie)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(videos -> {
                    Log.v("Reviews", "" + videos.size());
                });
    }

    public void detach() {
    }

    public void onFavoriteClick() {
        mRepository.setMovieFavorite(mMovie, true)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(() -> {
                    mView.hideFavoriteButton();
                    mView.showUnfavoriteButton();
                });
    }

    public void onUnfavoriteClick() {
        mRepository.setMovieFavorite(mMovie, false)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(() -> {
                    mView.hideUnfavoriteButton();
                    mView.showFavoriteButton();
                });
    }
}
