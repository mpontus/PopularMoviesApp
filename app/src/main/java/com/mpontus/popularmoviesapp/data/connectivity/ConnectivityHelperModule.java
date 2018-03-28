package com.mpontus.popularmoviesapp.data.connectivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ConnectivityHelperModule {
    @Provides
    ConnectivityHelper getConnectivityHelper(AppConnetivityHelper connetivityHelper) {
        return connetivityHelper;
    }
}
