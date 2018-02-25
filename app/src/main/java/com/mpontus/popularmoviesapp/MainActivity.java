package com.mpontus.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnClickListener {

    /**
     * Number of columns in the grid
     */
    public static final int SPAN_COUNT = 2;

    /**
     * TMDb API client
     */
    @Inject TMDbService tmdbService;

    /**
     * Recycler view for movie listing
     */
    @BindView(R.id.rvMovies) RecyclerView mMovieListView;

    /**
     * Recycler view adapter
     */
    private MovieListAdapter mMovieListAdapter = new MovieListAdapter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((PopularMoviesApplication) getApplication()).getAppComponent().inject(this);

        RecyclerView.LayoutManager movieListLayoutManager =
                new GridLayoutManager(this, SPAN_COUNT);

        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListView.setLayoutManager(movieListLayoutManager);

        tmdbService.getPopularMovies()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(response -> response.results)
                .subscribe(movies -> mMovieListAdapter.setMovies(movies));
    }

    @Override
    public void onClick(View v, Movie m) {
        Log.v("Movie Clicked", m.title);
    }
}
