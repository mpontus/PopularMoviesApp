package com.mpontus.popularmoviesapp;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class, TMDbServiceModule.class})
public interface AppComponent {
    void inject(MainActivity mainActivity);
}
