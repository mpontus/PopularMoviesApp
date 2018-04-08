package com.mpontus.popularmoviesapp.data;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;

import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity;

import javax.inject.Inject;

import io.reactivex.annotations.Nullable;

@ActivityScoped
public class Navigator {
    private Activity mActivity;

    @Inject
    Navigator(Activity activity) {
        mActivity = activity;
    }

    public void openMovieDetails(Movie movie, @Nullable View backdropView) {
        Intent intent = new Intent(mActivity, MovieDetailsActivity.class);

        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);

        if (backdropView != null) {
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation(mActivity, backdropView, "backdrop");

            mActivity.startActivity(intent, options.toBundle());
        } else {
            mActivity.startActivity(intent);
        }
    }
}
