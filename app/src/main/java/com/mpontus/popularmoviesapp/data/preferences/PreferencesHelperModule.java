package com.mpontus.popularmoviesapp.data.preferences;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class PreferencesHelperModule {
    @Binds
    abstract PreferencesHelper providePreferencesHelper(AppPreferencesHelper preferencesHelper);
}
