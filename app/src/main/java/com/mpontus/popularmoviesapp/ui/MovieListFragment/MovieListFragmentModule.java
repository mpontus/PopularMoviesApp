package com.mpontus.popularmoviesapp.ui.MovieListFragment;


import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.mpontus.popularmoviesapp.data.AppApiHelper;
import com.mpontus.popularmoviesapp.di.ApplicationContext;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import io.reactivex.Observable;

@Module
public abstract class MovieListFragmentModule {
    @Binds
    @FragmentScoped
    abstract MovieListFragmentContract.Presenter provideMovieListPresenter(MovieListFragmentPresenter presenter);

    @Binds
    @FragmentScoped
    abstract MovieListFragmentContract.View provideMovieListView(MovieListFragment fragment);

    @Provides
    @FragmentScoped
    static TMDbService.MovieSource provideMovieSource(MovieListFragment fragment) {
        return fragment.getMovieSource();
    }

    // We need to share movie list between Fragment and ViewHolder presenters.
    @Provides
    @FragmentScoped
    static Observable<List<Movie>> provideMovieList(AppApiHelper apiHelper, TMDbService.MovieSource movieSource) {
        return apiHelper.getMovies(movieSource).cache();
    }

    @Provides
    @FragmentScoped
    static RecyclerView.LayoutManager provideLayoutManager(@ApplicationContext Context context) {
        return new LinearLayoutManager(context);
    }
}
