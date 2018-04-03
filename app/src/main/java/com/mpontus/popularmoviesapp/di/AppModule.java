package com.mpontus.popularmoviesapp.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.preference.PreferenceManager;
import android.view.WindowManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

@Module
public class AppModule {
    @Provides
    @Singleton
    @ApplicationContext
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    ConnectivityManager provideConnectivityManager(@ApplicationContext Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(@ApplicationContext Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Provides
    @Singleton
    WindowManager provideWindowManager(@ApplicationContext Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }
}
