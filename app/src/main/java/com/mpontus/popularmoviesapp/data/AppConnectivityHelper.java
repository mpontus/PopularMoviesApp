package com.mpontus.popularmoviesapp.data;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.di.ApplicationContext;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScoped
public class AppConnectivityHelper {

    private final Context mContext;

    private final ConnectivityManager mConnectivityManager;

    @Inject
    AppConnectivityHelper(@ApplicationContext Context context,
                          @Nullable ConnectivityManager connectivityManager) {
        mContext = context;
        mConnectivityManager = connectivityManager;
    }

    public Observable<Boolean> getIsOnline() {
        if (mConnectivityManager == null) {
            return Observable.just(false);
        }

        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

        return getBroadcasts(new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
                .map(intent -> !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY,
                        false));
    }

    private Observable<Intent> getBroadcasts(IntentFilter intentFilter) {
        return Observable.create(emitter -> {
            BroadcastReceiver receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    emitter.onNext(intent);
                }
            };

            emitter.setCancellable(() -> mContext.unregisterReceiver(receiver));

            mContext.registerReceiver(receiver, intentFilter);
        });
    }
}