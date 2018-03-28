package com.mpontus.popularmoviesapp.di;

import android.app.Application;

import com.mpontus.popularmoviesapp.PopularMoviesApplication;
import com.mpontus.popularmoviesapp.tmdb.TMDbServiceModule;
import com.mpontus.popularmoviesapp.utils.SchedulersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        TMDbServiceModule.class,
        SchedulersModule.class,
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

    void inject(PopularMoviesApplication app);
}
