package com.mpontus.popularmoviesapp.ui.MovieList;

import android.net.Uri;

public interface MovieListItemContract {
    interface View {
        void setPoster(Uri posterUri);
    }

    interface Presenter {
        void attach();

        void detach();

        void onClick();
    }
}
