package com.mpontus.popularmoviesapp.ui.MovieListFragment;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mpontus.popularmoviesapp.di.ApplicationContext;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class MovieListFragmentModule {
    @Binds
    @FragmentScoped
    abstract MovieListFragmentContract.Presenter provideMovieListPresenter(MovieListPresenter presenter);

    @Binds
    @FragmentScoped
    abstract MovieListFragmentContract.View provideMovieListView(MovieListFragment fragment);

    @Provides
    @FragmentScoped
    static MovieSourceType provideMovieSource(MovieListFragment fragment) {
        return fragment.getMovieSource();
    }

    // We need to share movie list between Fragment and ViewHolder presenters.
//    @Provides
//    @FragmentScoped
//    static Observable<List<Movie>> provideMovieList(MovieRepository movieRepository, MovieListFragment.MovieSource movieSource) {
//        switch (movieSource)
//        return apiHelper.getMovies(movieSource).cache();
//    }

    @Provides
    @FragmentScoped
    static RecyclerView.LayoutManager provideLayoutManager(@ApplicationContext Context context) {
        return new LinearLayoutManager(context);
    }
}
