package com.mpontus.popularmoviesapp.di;

import com.mpontus.popularmoviesapp.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TMDbServiceModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
