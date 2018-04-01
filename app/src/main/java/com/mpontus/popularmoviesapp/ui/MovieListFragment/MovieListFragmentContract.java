package com.mpontus.popularmoviesapp.ui.MovieListFragment;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

public interface MovieListFragmentContract {
    interface View {
        void showOffline();

        void showLoading();

        void showMovies();

        void setMovies(List<Movie> movies);

        void openDetailActivity(Movie movie);
    }

    interface Presenter {
        void attach();

        void detach();

        void onMovieSourceChange(TMDbService.MovieSource source);

        void onMovieClick(Movie movie);
    }
}
