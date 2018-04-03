package com.mpontus.popularmoviesapp.data;

import android.content.SharedPreferences;

import com.mpontus.popularmoviesapp.tmdb.TMDbService;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

@Singleton
public class AppPreferencesHelper {

    private static final String PREF_KEY_MOVIE_SOURCE = "PREF_KEY_MOVIE_SOURCE";

    private final SharedPreferences mPreferences;

    @Inject
    AppPreferencesHelper(SharedPreferences preferences) {
        mPreferences = preferences;
    }

    public Observable<TMDbService.MovieSource> getMovieSource() {
        Observable<String> keyChanges = Observable.create((ObservableOnSubscribe<String>) emitter -> {
            final SharedPreferences.OnSharedPreferenceChangeListener listener =
                    (sharedPreferences, key) -> emitter.onNext(key);

            emitter.setCancellable(() -> mPreferences.unregisterOnSharedPreferenceChangeListener(listener));

            mPreferences.registerOnSharedPreferenceChangeListener(listener);
        }).startWith(PREF_KEY_MOVIE_SOURCE);


        return keyChanges.filter(key -> key.equals(PREF_KEY_MOVIE_SOURCE))
                .map(s -> mPreferences.getInt(PREF_KEY_MOVIE_SOURCE, -1))
                .map(TMDbService.MovieSource::fromValue);
    }

    public void setMovieSource(TMDbService.MovieSource source) {
        mPreferences.edit().putInt(PREF_KEY_MOVIE_SOURCE, source.getValue()).apply();
    }
}
