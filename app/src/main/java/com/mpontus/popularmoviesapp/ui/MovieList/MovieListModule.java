package com.mpontus.popularmoviesapp.ui.MovieList;


import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.ui.MovieListFragment.MovieListFragment;
import com.mpontus.popularmoviesapp.ui.MovieListFragment.MovieListFragmentModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieListModule {
    @FragmentScoped
    @ContributesAndroidInjector(modules = MovieListFragmentModule.class)
    abstract MovieListFragment movieListFragment();
}
