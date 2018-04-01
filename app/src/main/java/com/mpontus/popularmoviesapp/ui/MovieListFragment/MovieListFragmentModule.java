package com.mpontus.popularmoviesapp.ui.MovieListFragment;


import dagger.Binds;
import dagger.Module;

@Module
public abstract class MovieListFragmentModule {
    @Binds
    abstract MovieListFragmentContract.Presenter provideMovieListPresenter(MovieListFragmentPresenter presenter);

    @Binds
    abstract MovieListFragmentContract.View provideMovieListView(MovieListFragment fragment);
}
