package com.mpontus.popularmoviesapp.data.preferences;

import android.content.SharedPreferences;

import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import javax.inject.Inject;

class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_MOVIE_SOURCE = "PREF_KEY_MOVIE_SOURCE";

    private final SharedPreferences mPreferences;

    @Inject
    AppPreferencesHelper(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    @Override
    public TMDbService.MovieSource getMovieSource() {
        return TMDbService.MovieSource.fromValue(
                mPreferences.getInt(PREF_KEY_MOVIE_SOURCE, 0)
        );
    }

    @Override
    public void setMovieSource(TMDbService.MovieSource source) {
        mPreferences.edit().putInt(PREF_KEY_MOVIE_SOURCE, source.getValue()).apply();
    }

    @Override
    public void onMovieSourceChange(OnPreferenceChangeListener<TMDbService.MovieSource> listener) {
        mPreferences.registerOnSharedPreferenceChangeListener(
                (sharedPreferences, key) -> {
                    if (!key.equals(PREF_KEY_MOVIE_SOURCE)) {
                        return;
                    }

                    listener.onPreferenceChange(getMovieSource());
                }
        );
    }
}
