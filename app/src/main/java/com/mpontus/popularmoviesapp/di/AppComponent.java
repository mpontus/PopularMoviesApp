package com.mpontus.popularmoviesapp.di;

import android.app.Application;

import com.mpontus.popularmoviesapp.PopularMoviesApplication;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;

@Component(modules = {AppModule.class, ActivityBindingModule.class})
public interface AppComponent extends AndroidInjector<PopularMoviesApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
