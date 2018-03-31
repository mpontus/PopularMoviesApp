package com.mpontus.popularmoviesapp.data.preferences;

import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import io.reactivex.Observable;

public interface PreferencesHelper {
    Observable<TMDbService.MovieSource> getMovieSource();

    void setMovieSource(TMDbService.MovieSource source);
}
