package com.mpontus.popularmoviesapp.ui.MovieDetails;

import com.mpontus.popularmoviesapp.tmdb.Movie;

import dagger.Module;
import dagger.Provides;

import static com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity.EXTRA_MOVIE;

@Module
public abstract class MovieDetailsModule {
    @Provides
    static Movie provideMovie(MovieDetailsActivity activity) {
        return (Movie) activity.getIntent().getParcelableExtra(EXTRA_MOVIE);
    }
}
