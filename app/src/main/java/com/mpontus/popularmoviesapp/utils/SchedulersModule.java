package com.mpontus.popularmoviesapp.utils;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {
    @Provides
    @Named("MAIN")
    Scheduler provideMainThreadScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Named("BACKGROUND")
    Scheduler provideBackgroundThreadScheduler() {
        return Schedulers.io();
    }
}
