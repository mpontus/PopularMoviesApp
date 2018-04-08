package com.mpontus.popularmoviesapp.data;

import com.mpontus.popularmoviesapp.data.local.LocalMovieRepository;
import com.mpontus.popularmoviesapp.data.remote.RemoteMovieRepository;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.Review;
import com.mpontus.popularmoviesapp.tmdb.Video;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Observable;

@Singleton
public class MovieRepository {
    private final RemoteMovieRepository mRemoteRepository;
    private final LocalMovieRepository mLocalRepository;

    private Observable<List<Movie>> mPopularMoviesCache = null;
    private Observable<List<Movie>> mTopRatedMoviesCache = null;
    private Observable<List<Movie>> mFavoriteMoviesCache = null;

    @Inject
    public MovieRepository(RemoteMovieRepository remoteRepository,
                           LocalMovieRepository localRepository) {
        mRemoteRepository = remoteRepository;
        mLocalRepository = localRepository;
    }

    public Observable<List<Movie>> getPopularMovies() {
        if (mPopularMoviesCache == null) {
            mPopularMoviesCache = mRemoteRepository.getPopularMovies().cache();
        }

        return mPopularMoviesCache;
    }

    public Observable<List<Movie>> getTopRatedMovies() {
        if (mTopRatedMoviesCache == null) {
            mTopRatedMoviesCache = mRemoteRepository.getTopRatedMovies().cache();
        }

        return mTopRatedMoviesCache;
    }

    public Observable<List<Movie>> getFavoriteMovies() {
        if (mFavoriteMoviesCache == null) {
            mFavoriteMoviesCache = mLocalRepository.getFavoriteMovies().cache();
        }

        return mFavoriteMoviesCache;
    }

    public Observable<Boolean> isMovieFavorite(Movie movie) {
        return mLocalRepository.isMovieFavorite(movie);
    }

    public Completable setMovieFavorite(Movie movie, boolean isFavorite) {
        if (isFavorite) {
            return mLocalRepository.favoriteMovie(movie);
        } else {
            return mLocalRepository.unfavoriteMovie(movie);
        }
    }

    public Observable<List<Review>> getMovieReviews(Movie movie) {
        return mRemoteRepository.getMovieReviews(movie);
    }

    public Observable<List<Video>> getMovieVideos(Movie movie) {
        return mRemoteRepository.getMovieVideos(movie);
    }
}
