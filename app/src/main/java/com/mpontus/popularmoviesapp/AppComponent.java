package com.mpontus.popularmoviesapp;

import dagger.Component;

@Component(modules = TMDbServiceModule.class)
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
