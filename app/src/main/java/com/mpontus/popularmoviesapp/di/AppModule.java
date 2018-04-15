package com.mpontus.popularmoviesapp.di;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.view.Display;
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
    WindowManager provideWindowManager(@ApplicationContext Context context) {
        return (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    }

    @Provides
    @Singleton
    Display provideDisplay(WindowManager windowManager) {
        return windowManager.getDefaultDisplay();
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    ContentResolver provideContentResolver(@ApplicationContext Context context) {
        return context.getContentResolver();
    }
}
