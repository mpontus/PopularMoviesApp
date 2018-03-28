package com.mpontus.popularmoviesapp.ui.MovieList;

import dagger.Module;
import dagger.Provides;

@Module
public class MovieListModule {
    @Provides
    MovieListContract.Presenter provideMovieListPresenter(MovieListPresenter presenter) {
        return presenter;
    }
}
