package com.mpontus.popularmoviesapp.di;

import com.mpontus.popularmoviesapp.ui.MovieList.MovieListActivity;
import com.mpontus.popularmoviesapp.ui.MovieList.MovieListModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {
    @ContributesAndroidInjector(modules = MovieListModule.class)
    abstract MovieListActivity moveListActivity();
}
