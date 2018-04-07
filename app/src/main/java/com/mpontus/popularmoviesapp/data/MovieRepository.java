package com.mpontus.popularmoviesapp.data;

import com.mpontus.popularmoviesapp.data.remote.RemoteMovieRepository;
import com.mpontus.popularmoviesapp.tmdb.Movie;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;

@Singleton
public class MovieRepository {
    private final RemoteMovieRepository mRemoteRepository;

    @Inject
    public MovieRepository(RemoteMovieRepository remoteDataSource) {
        mRemoteRepository = remoteDataSource;
    }

    public Observable<List<Movie>> getPopularMovies() {
        return mRemoteRepository.getPopularMovies();
    }

    public Observable<List<Movie>> getTopRatedMovies() {
        return mRemoteRepository.getTopRatedMovies();
    }
}
