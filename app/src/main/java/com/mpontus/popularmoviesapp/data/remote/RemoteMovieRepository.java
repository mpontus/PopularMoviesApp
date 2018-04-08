package com.mpontus.popularmoviesapp.data.remote;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.Review;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;
import com.mpontus.popularmoviesapp.tmdb.Video;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class RemoteMovieRepository {
    private TMDbService mApiService;

    @Inject
    public RemoteMovieRepository(TMDbService apiService) {
        mApiService = apiService;
    }

    public Observable<List<Movie>> getPopularMovies() {
        return mApiService.getPopularMovies().map(response -> response.results);
    }

    public Observable<List<Movie>> getTopRatedMovies() {
        return mApiService.getTopRatedMovies().map(response -> response.results);
    }

    public Observable<List<Review>> getMovieReviews(Movie movie) {
        return mApiService.getMovieReviews(Integer.toString(movie.id))
                .map(response -> response.results);
    }

    public Observable<List<Video>> getMovieVideos(Movie movie) {
        return mApiService.getMovieVideos(Integer.toString(movie.id))
                .map(response -> response.results);
    }
}
