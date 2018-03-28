package com.mpontus.popularmoviesapp.data.preferences;

import com.mpontus.popularmoviesapp.tmdb.TMDbService;

public interface PreferencesHelper {
    TMDbService.MovieSource getMovieSource();

    void setMovieSource(TMDbService.MovieSource source);

    void onMovieSourceChange(OnPreferenceChangeListener<TMDbService.MovieSource> listener);

    interface OnPreferenceChangeListener<T> {
        void onPreferenceChange(T value);
    }
}
