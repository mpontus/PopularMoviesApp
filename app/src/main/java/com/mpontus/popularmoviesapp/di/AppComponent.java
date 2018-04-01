package com.mpontus.popularmoviesapp.di;

import android.app.Application;

import com.mpontus.popularmoviesapp.PopularMoviesApplication;
import com.mpontus.popularmoviesapp.tmdb.TMDbServiceModule;
import com.mpontus.popularmoviesapp.utils.SchedulersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AppModule.class,
        ActivityBindingModule.class,
        TMDbServiceModule.class,
        SchedulersModule.class,
        AndroidSupportInjectionModule.class,
})
public interface AppComponent extends AndroidInjector<PopularMoviesApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
