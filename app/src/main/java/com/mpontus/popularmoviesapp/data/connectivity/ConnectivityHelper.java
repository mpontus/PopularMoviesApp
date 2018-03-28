package com.mpontus.popularmoviesapp.data.connectivity;

public interface ConnectivityHelper {
    boolean isOnline();

    void onOnlineStatusChange(OnlineStatusChangeListener listener);

    public interface OnlineStatusChangeListener {
        void onOnlineStatusChange(boolean isOnline);
    }
}
