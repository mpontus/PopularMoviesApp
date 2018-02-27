package com.mpontus.popularmoviesapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    @Singleton
    RxSharedPreferences provideRxSharedPreferences() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);

        return RxSharedPreferences.create(preferences);
    }

    @Provides
    @Singleton
    @Named("sort_order")
    Preference<Integer> provideSortOrderPreference(RxSharedPreferences rxPreferences) {
        return rxPreferences.getInteger("sort_order");
    }
}
