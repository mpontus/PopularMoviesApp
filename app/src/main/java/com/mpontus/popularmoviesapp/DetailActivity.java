package com.mpontus.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "EXTRA_MOVIE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        Log.v("MOVIE TITLE", movie.title);
    }
}
