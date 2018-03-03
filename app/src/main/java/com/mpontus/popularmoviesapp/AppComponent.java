package com.mpontus.popularmoviesapp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = TMDbServiceModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
