package com.mpontus.popularmoviesapp.ui.MovieList;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

public interface MovieListContract {
    interface View {
        void showOffline();

        void showLoading();

        void showMovies();

        void setMovieSource(TMDbService.MovieSource source);

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
