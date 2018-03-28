package com.mpontus.popularmoviesapp.ui.MovieDetails;

import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import javax.inject.Inject;

@ActivityScoped
public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private MovieDetailsContract.View mView;
    private Movie mMovie;

    @Inject
    MovieDetailsPresenter(MovieDetailsContract.View view, Movie movie) {
        mView = view;
        mMovie = movie;
    }

    @Override
    public void attach() {
        mView.setTitle(mMovie.title, mMovie.getReleaseYear());
        mView.setBackdrop(mMovie.getBackdropUrl());
        mView.setPoster(mMovie.getPosterUrl());
        mView.setRating(mMovie.voteAverage, mMovie.voteCount);
        mView.setDescription(mMovie.overview);
    }

    @Override
    public void detach() {
    }
}
