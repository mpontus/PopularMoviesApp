package com.mpontus.popularmoviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnClickListener {

    public static final int SORT_ORDER_POPULAR = 1;
    public static final int SORT_ORDER_RECENT = 2;

    // TODO: Make correlation between those values and array of options in stirngs.xml more explicit
    // E.g. Custom adapter as an inner class
    public static final int[] SORT_ORDER_VALUES = {
            SORT_ORDER_POPULAR,
            SORT_ORDER_RECENT,
    };

    /**
     * Number of columns in the grid
     */
    public static final int SPAN_COUNT = 2;

    /**
     * TMDb API client
     */
    @Inject TMDbService tmdbService;

    /**
     * Movie category selector
     */
    @BindView(R.id.spSortOrder) Spinner mSortOrderView;

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

        ArrayAdapter<CharSequence> sortOrderAdapter =
                ArrayAdapter.createFromResource(this, R.array.movie_source_options,
                        android.R.layout.simple_spinner_dropdown_item);

        mSortOrderView.setAdapter(sortOrderAdapter);

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
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.EXTRA_MOVIE, m);

        startActivity(intent);
    }
}
