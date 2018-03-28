package com.mpontus.popularmoviesapp.data.network;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ApiHelperModule {
    @Binds
    abstract ApiHelper provideApiHelper(AppApiHelper helper);
}
