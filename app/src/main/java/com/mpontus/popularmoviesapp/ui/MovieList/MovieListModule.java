package com.mpontus.popularmoviesapp.ui.MovieList;


import android.content.Context;

import com.mpontus.popularmoviesapp.di.ActivityContext;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.di.FragmentScoped;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MovieListModule {
    @Binds
    @ActivityScoped
    @ActivityContext
    abstract Context provideActivityContext(MovieListActivity activity);

    @FragmentScoped
    @ContributesAndroidInjector(modules = MovieListFragmentModule.class)
    abstract MovieListFragment movieListFragment();
}
