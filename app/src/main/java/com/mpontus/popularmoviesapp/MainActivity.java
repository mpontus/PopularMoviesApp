package com.mpontus.popularmoviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.f2prateek.rx.preferences2.Preference;
import com.f2prateek.rx.preferences2.RxSharedPreferences;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.reactivex.Observable;
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

    Preference<Integer> mSortOrderPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((PopularMoviesApplication) getApplication()).getAppComponent().inject(this);

        // Initialize movie sort order selector
        ArrayAdapter<CharSequence> sortOrderAdapter =
                ArrayAdapter.createFromResource(this, R.array.movie_source_options,
                        android.R.layout.simple_spinner_dropdown_item);

        mSortOrderView.setAdapter(sortOrderAdapter);


        // Initialize recycler view
        RecyclerView.LayoutManager movieListLayoutManager =
                new GridLayoutManager(this, SPAN_COUNT);

        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListView.setLayoutManager(movieListLayoutManager);
        mMovieListView.setNestedScrollingEnabled(false);

        // Test preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mSortOrderPreference = RxSharedPreferences.create(preferences)
                .getInteger(getString(R.string.pref_sort_order_key));

        mSortOrderPreference
                .asObservable()
                .subscribe(value -> Log.v("VALUE", String.valueOf(value)));

        // Initialize service
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

    @OnItemSelected(R.id.spSortOrder)
    public void onItemSelected(Spinner spinner, int position) {
        int value = SORT_ORDER_VALUES[position];

        Log.v("SETTING THE VALUE", String.valueOf(value));

        switch (value) {
            case SORT_ORDER_POPULAR:
            case SORT_ORDER_RECENT:
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putInt(getString(R.string.pref_sort_order_key), value)
                        .apply();

                return;

            default:
                throw new RuntimeException("Unexpected option: " + value);
        }
    }
}
