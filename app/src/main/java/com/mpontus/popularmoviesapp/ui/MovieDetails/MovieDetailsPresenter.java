package com.mpontus.popularmoviesapp.ui.MovieDetails;

import com.mpontus.popularmoviesapp.data.MovieRepository;
import com.mpontus.popularmoviesapp.data.Navigator;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.Review;
import com.mpontus.popularmoviesapp.tmdb.Video;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;

@ActivityScoped
public class MovieDetailsPresenter implements
        MovieDetailsContract.Presenter,
        MovieDetailsContract.ReviewItemPresenterFactory,
        MovieDetailsContract.TrailerItemPresenterFactory {

    private final MovieRepository mRepository;
    private final Scheduler mMainThreadScheduler;
    private final Scheduler mBackgroundThreadScheduler;
    private final Navigator mNavigator;
    private MovieDetailsContract.View mView;
    private Movie mMovie;
    private boolean mIsFavorite;
    private List<Review> mReviews;
    private List<Video> mVideos;

    @Inject
    MovieDetailsPresenter(MovieRepository repository,
                          @Named("MAIN") Scheduler mainThreadScheduler,
                          @Named("BACKGROUND") Scheduler backgroundThreadScheduler,
                          Navigator navigator,
                          MovieDetailsContract.View view,
                          Movie movie) {
        mRepository = repository;
        mMainThreadScheduler = mainThreadScheduler;
        mBackgroundThreadScheduler = backgroundThreadScheduler;
        mNavigator = navigator;
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
                    mIsFavorite = isFavorite;

                    mView.setFavoriteChecked(isFavorite);
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
                    mVideos = videos;

                    mView.setVideoCount(videos.size());
                });
    }

    public void detach() {
    }

    @Override
    public void onFavoriteChanged(boolean isChecked) {
        if (isChecked == mIsFavorite) {
            return;
        }

        mRepository.setMovieFavorite(mMovie, isChecked)
                .subscribeOn(mBackgroundThreadScheduler)
                .observeOn(mMainThreadScheduler)
                .subscribe(() -> {
                    mIsFavorite = isChecked;
                });
    }

    @Override
    public MovieDetailsContract.ReviewItemPresenter createReviewItemPresenter(
            MovieDetailsContract.ReviewItemView view, int position) {
        return new ReviewViewHolderPresenter(view, mReviews.get(position));
    }

    @Override
    public MovieDetailsContract.TrailerItemPresenter createTrailerItemPresenter(MovieDetailsContract.TrailerItemView view, int position) {
        return new TrailerViewHolderPresenter(view, mVideos.get(position));
    }

    public class ReviewViewHolderPresenter implements MovieDetailsContract.ReviewItemPresenter {
        private final MovieDetailsContract.ReviewItemView mView;
        private final Review mReview;

        ReviewViewHolderPresenter(MovieDetailsContract.ReviewItemView view, Review review) {
            mView = view;
            mReview = review;
        }

        @Override
        public void attach() {
            mView.setContent(mReview.content);
        }

        @Override
        public void detach() {
        }
    }

    public class TrailerViewHolderPresenter implements MovieDetailsContract.TrailerItemPresenter {
        private final MovieDetailsContract.TrailerItemView mView;
        private final Video mVideo;

        TrailerViewHolderPresenter(MovieDetailsContract.TrailerItemView view, Video video) {
            mView = view;
            mVideo = video;
        }

        @Override
        public void attach() {
            switch (mVideo.site) {
                case "YouTube":
                    mView.setYoutubeVideoId(mVideo.key);

                    break;

                default:
                    throw new RuntimeException("Unsupported video site: " + mVideo.site);
            }
        }

        @Override
        public void detach() {

        }

        @Override
        public void onClick() {
            mNavigator.openYoutube(mVideo.key);
        }
    }
}
