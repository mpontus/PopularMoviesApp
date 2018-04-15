package com.mpontus.popularmoviesapp.ui.MovieList;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;

import com.mpontus.popularmoviesapp.di.ActivityContext;
import com.mpontus.popularmoviesapp.di.ActivityScoped;

import javax.inject.Inject;

@ActivityScoped
public class LayoutManagerFactory {
    private static final int MAX_CARD_WIDTH = 144;

    private final Context mContext;
    private final Display mDisplay;

    @Inject
    LayoutManagerFactory(@ActivityContext Context context, Display display) {
        mContext = context;
        mDisplay = display;
    }

    RecyclerView.LayoutManager getLayoutManager() {
        return new GridLayoutManager(mContext, getSpanCount());
    }

    private int getSpanCount() {
        DisplayMetrics metrics = new DisplayMetrics();

        mDisplay.getMetrics(metrics);

        return ((Float) (metrics.widthPixels / metrics.density / MAX_CARD_WIDTH)).intValue();
    }
}
