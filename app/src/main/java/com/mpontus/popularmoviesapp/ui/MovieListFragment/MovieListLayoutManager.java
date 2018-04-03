package com.mpontus.popularmoviesapp.ui.MovieListFragment;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.mpontus.popularmoviesapp.di.ActivityContext;

import javax.inject.Inject;


public class MovieListLayoutManager extends GridLayoutManager {
    public static final int MAX_CARD_WIDTH = 144;

    private static int getSpanCount(WindowManager windowManager) {
        DisplayMetrics metrics = new DisplayMetrics();
        Display display = windowManager.getDefaultDisplay();

        display.getMetrics(metrics);

        return ((Float) (metrics.widthPixels / metrics.density / MAX_CARD_WIDTH)).intValue();
    }

    @Inject
    public MovieListLayoutManager(@ActivityContext Context context, WindowManager windowManager) {
        super(context, getSpanCount(windowManager));
    }
}
