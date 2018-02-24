package com.mpontus.popularmoviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnClickListener {

    private MovieListAdapter mMovieListAdapter;

    @Inject
    TMDbService tmdbService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PopularMoviesApplication) getApplication()).getAppComponent().inject(this);

        mMovieListAdapter = new MovieListAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);

        RecyclerView movieList = findViewById(R.id.rvMovies);
        movieList.setAdapter(mMovieListAdapter);
        movieList.setLayoutManager(layoutManager);

        tmdbService.getPopularMovies(BuildConfig.TMDB_API_KEY)
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
