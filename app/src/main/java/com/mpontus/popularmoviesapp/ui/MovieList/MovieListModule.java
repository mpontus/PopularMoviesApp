package com.mpontus.popularmoviesapp.ui.MovieList;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class MovieListModule {
    @Binds
    abstract MovieListContract.Presenter provideMovieListPresenter(MovieListPresenter presenter);

    @Binds
    abstract MovieListContract.View provideMovieListView(MovieListActivity activity);
}
