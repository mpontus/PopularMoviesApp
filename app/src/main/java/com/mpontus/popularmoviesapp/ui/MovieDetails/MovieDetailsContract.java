package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.net.Uri;

interface MovieDetailsContract {
    interface View {
        void setTitle(String title, int year);

        void setBackdrop(Uri backdropUrl);

        void setPoster(Uri posterUrl);

        void setRating(float voteAverage, int voteCount);

        void setDescription(String description);
    }

    interface Presenter {
        void attach();

        void detach();

        void onFavoriteClick();
    }
}
