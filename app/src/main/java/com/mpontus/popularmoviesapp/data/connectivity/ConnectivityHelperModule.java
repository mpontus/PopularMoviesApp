package com.mpontus.popularmoviesapp.data.connectivity;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ConnectivityHelperModule {
    @Binds
    abstract ConnectivityHelper getConnectivityHelper(AppConnetivityHelper connetivityHelper);
}
