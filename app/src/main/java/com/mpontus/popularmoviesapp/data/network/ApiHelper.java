package com.mpontus.popularmoviesapp.data.network;

import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import java.util.List;

import io.reactivex.Observable;

public interface ApiHelper {
    Observable<List<Movie>> getMovies(TMDbService.MovieSource source);
}
