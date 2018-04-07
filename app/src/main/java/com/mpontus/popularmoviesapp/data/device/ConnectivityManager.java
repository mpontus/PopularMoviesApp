package com.mpontus.popularmoviesapp.data.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.mpontus.popularmoviesapp.di.ApplicationContext;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ConnectivityManager {
    private final Context mContext;

    @Inject
    ConnectivityManager(@ApplicationContext Context context) {
        mContext = context;
    }

    public Observable<Boolean> getIsOnline() {
        return getBroadcasts(new IntentFilter(android.net.ConnectivityManager.CONNECTIVITY_ACTION))
                .map(intent -> !intent.getBooleanExtra(android.net.ConnectivityManager.EXTRA_NO_CONNECTIVITY,
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
