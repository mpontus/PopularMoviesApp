package com.mpontus.popularmoviesapp.data;

import android.content.Context;
import android.content.Intent;

import com.mpontus.popularmoviesapp.di.ActivityContext;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity;

import javax.inject.Inject;

@ActivityScoped
public class Navigator {
    private Context mContext;

    @Inject
    Navigator(@ActivityContext Context context) {
        mContext = context;
    }

    public void openMovieDetails(Movie movie) {
        Intent intent = new Intent(mContext, MovieDetailsActivity.class);

        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);

        mContext.startActivity(intent);
    }
}
