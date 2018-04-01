package com.mpontus.popularmoviesapp.ui.MovieListFragment;


import android.os.Bundle;

import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MovieListFragmentModule {
    @Binds
    abstract MovieListFragmentContract.Presenter provideMovieListPresenter(MovieListFragmentPresenter presenter);

    @Binds
    abstract MovieListFragmentContract.View provideMovieListView(MovieListFragment fragment);

    @Provides
    static TMDbService.MovieSource provideMovieSource(MovieListFragment fragment) {
        Bundle args = fragment.getArguments();

        if (args == null) {
            return null;
        }

        int value = args.getInt(MovieListFragment.ARG_MOVIE_SOURCE, -1);

        if (value == -1) {
            return null;
        }

        return TMDbService.MovieSource.fromValue(value);
    }
}
