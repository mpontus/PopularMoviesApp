package com.mpontus.popularmoviesapp.data.connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;

import com.mpontus.popularmoviesapp.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class AppConnetivityHelper implements ConnectivityHelper {

    private final Context mContext;

    private final ConnectivityManager mConnectivityManager;

    @Inject
    AppConnetivityHelper(Context context,
                         @Nullable ConnectivityManager connectivityManager) {
        mContext = context;
        mConnectivityManager = connectivityManager;
    }

    @Override
    public boolean isOnline() {
        if (mConnectivityManager == null) {
            return false;
        }

        NetworkInfo networkInfo = mConnectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    // TODO: This receiver will need to be unregistered at some point
    // Maybe by using activity context?
    @Override
    public void onOnlineStatusChange(OnlineStatusChangeListener listener) {
        mContext.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean isOffline = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                listener.onOnlineStatusChange(!isOffline);
            }
        }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }
}
