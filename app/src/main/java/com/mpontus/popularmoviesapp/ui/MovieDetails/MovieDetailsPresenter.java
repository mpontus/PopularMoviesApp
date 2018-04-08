package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.util.Log;

import com.mpontus.popularmoviesapp.data.MovieRepository;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.Review;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

@ActivityScoped
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter, MovieDetailsContract.ReviewItemPresenterFactory {

    private final MovieRepository mRepository;
    private final Scheduler mMainThreadScheduler;
    private final Scheduler mBackgroundThreadScheduler;
    private MovieDetailsContract.View mView;
    private Movie mMovie;
    private List<Review> mReviews;

    @Inject
    MovieDetailsPresenter(MovieRepository repository,
                          @Named("MAIN") Scheduler mainThreadScheduler,
                          @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                          MovieDetailsContract.View view,
                          Movie movie) {
        mRepository = repository;
        mMainThreadScheduler = mainThreadScheduler;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mView = view;
        mMovie = movie;
    }

    public void attach() {
        mView.setTitle(mMovie.title);
        mView.setBackdrop(mMovie.backdropPath);
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
                    mReviews = reviews;

                    mView.setReviewCount(reviews.size());
                });

        mRepository.getMovieVideos(mMovie)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(videos -> {
                    Log.v("Videos", "" + videos.size());
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

    @Override
    public MovieDetailsContract.ReviewItemPresenter createReviewItemPresenter(
            MovieDetailsContract.ReviewItemView view, int position) {
        return new ReviewViewHolderPresenter(view, mReviews.get(position));
    }

    public class ReviewViewHolderPresenter implements MovieDetailsContract.ReviewItemPresenter {
        private final MovieDetailsContract.ReviewItemView mView;
        private final Review mReview;

        ReviewViewHolderPresenter(MovieDetailsContract.ReviewItemView view, Review review) {
            mView = view;
            mReview = review;
        }

        public void attach() {
            mView.setContent(mReview.content);
        }

        public void detach() {
        }
    }
}
