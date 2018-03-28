package com.mpontus.popularmoviesapp.di;

import com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity;
import com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsModule;
import com.mpontus.popularmoviesapp.ui.MovieList.MovieListActivity;
import com.mpontus.popularmoviesapp.ui.MovieList.MovieListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = MovieListModule.class)
    abstract MovieListActivity movieListActivity();

    @ActivityScoped
    @ContributesAndroidInjector(modules = MovieDetailsModule.class)
    abstract MovieDetailsActivity movieDetailsActivity();
}
