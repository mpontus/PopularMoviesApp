package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.app.Activity;

import com.mpontus.popularmoviesapp.tmdb.Movie;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import static com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity.EXTRA_MOVIE;

@Module
public abstract class MovieDetailsModule {
    @Binds
    abstract Activity provideActivity(MovieDetailsActivity activity);

    @Binds
    abstract MovieDetailsContract.Presenter provideMovieDetailsPresenter(MovieDetailsPresenter presenter);

    @Binds
    abstract MovieDetailsContract.View provideMovieDetailsView(MovieDetailsActivity activity);

    @Binds
    abstract MovieDetailsContract.ReviewItemPresenterFactory provideReviewItemPresenterFactory(MovieDetailsPresenter presenter);

    @Binds
    abstract MovieDetailsContract.TrailerItemPresenterFactory provideTrailerItemPresenterFactory(MovieDetailsPresenter presenter);

    @Provides
    static Movie provideMovie(MovieDetailsActivity activity) {
        return (Movie) activity.getIntent().getParcelableExtra(EXTRA_MOVIE);
    }
}
