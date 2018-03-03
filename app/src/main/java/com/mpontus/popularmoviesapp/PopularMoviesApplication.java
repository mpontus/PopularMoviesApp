package com.mpontus.popularmoviesapp;

import android.app.Application;

public class PopularMoviesApplication extends Application {
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .tMDbServiceModule(new TMDbServiceModule(getString(R.string.tmdb_base_url),
                        BuildConfig.TMDB_API_KEY))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
