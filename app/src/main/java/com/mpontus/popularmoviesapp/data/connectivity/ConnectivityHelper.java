package com.mpontus.popularmoviesapp.data.connectivity;

import io.reactivex.Observable;

public interface ConnectivityHelper {
    Observable<Boolean> getIsOnline();
}
