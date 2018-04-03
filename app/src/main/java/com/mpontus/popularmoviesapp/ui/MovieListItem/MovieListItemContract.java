package com.mpontus.popularmoviesapp.ui.MovieListItem;

public interface MovieListItemContract {
    interface View {
        void setTitle(String title);

        void setBackdrop(String backdropPath);
    }

    interface Presenter {
        void attach();

        void detach();

        void onClick();
    }
}
