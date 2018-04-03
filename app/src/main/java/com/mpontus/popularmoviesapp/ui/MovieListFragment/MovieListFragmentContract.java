package com.mpontus.popularmoviesapp.ui.MovieListFragment;

public interface MovieListFragmentContract {
    interface View {
        void showOffline();

        void showLoading();

        void showMovies();

        void setMovieCount(int count);
    }

    interface Presenter {
        void attach();

        void detach();
    }
}
