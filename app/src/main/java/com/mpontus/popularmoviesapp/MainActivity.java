package com.mpontus.popularmoviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.f2prateek.rx.preferences2.Preference;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import butterknife.Optional;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.Nullable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnClickListener {

    public static final int SORT_ORDER_POPULAR = 1;
    public static final int SORT_TOP_RATED = 2;

    // TODO: Make correlation between those values and array of options in stirngs.xml more explicit
    // E.g. Custom adapter as an inner class
    public static final int[] SORT_ORDER_VALUES = {
            SORT_ORDER_POPULAR,
            SORT_TOP_RATED,
    };

    /**
     * Number of columns in the grid
     */
    public static final int SPAN_COUNT = 2;
    public static final String SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER = "MOVIE_LIST_LAYOUT_MANAGER";

    /**
     * TMDb API client
     */
    @Inject TMDbService tmdbService;

    /**
     * Movie category selector
     */
    @Nullable
    @BindView(R.id.spSortOrder)
    Spinner mSortOrderView;

    /**
     * Recycler view for movie listing
     */
    @Nullable
    @BindView(R.id.rvMovies)
    RecyclerView mMovieListView;

    /**
     * Saved recycler view position
     */
    private Bundle mSavedInstanceState;

    /**
     * Recycler view adapter
     */
    private MovieListAdapter mMovieListAdapter = new MovieListAdapter(this);

    @Inject
    @Named("sort_order")
    Preference<Integer> mSortOrderPreference;
    private RecyclerView.LayoutManager mMovieListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSavedInstanceState = savedInstanceState;

        tryNetwork();
    }

    @Optional
    @OnClick(R.id.btnTryNetwork)
    protected void tryNetwork() {
        if (!isNetworkAvailable()) {
            setContentView(R.layout.activity_main_without_network);
            ButterKnife.bind(this);

            return;
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((PopularMoviesApplication) getApplication()).getAppComponent().inject(this);

        // Initialize movie sort order selector
        ArrayAdapter<CharSequence> sortOrderAdapter =
                ArrayAdapter.createFromResource(this, R.array.movie_sort_order_options,
                        android.R.layout.simple_spinner_dropdown_item);


        mSortOrderView.setAdapter(sortOrderAdapter);

        // Initialize recycler view
        mMovieListLayoutManager = new GridLayoutManager(this, SPAN_COUNT);

        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListView.setLayoutManager(mMovieListLayoutManager);
        mMovieListView.setNestedScrollingEnabled(false);

        mSortOrderPreference
                .asObservable()
                .observeOn(Schedulers.newThread())
                .switchMap(sortOrder -> {
                    if (sortOrder == SORT_ORDER_POPULAR) {
                        return tmdbService.getPopularMovies();
                    } else {
                        return tmdbService.getTopRatedMovies();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Toast.makeText(this, R.string.fetching_error_message, Toast.LENGTH_SHORT)
                            .show();
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(response -> {
                    mMovieListAdapter.setMovies(response.results);

                    if (mSavedInstanceState == null) {
                        return;
                    }

                    Parcelable movieListLayoutManagerState =
                            mSavedInstanceState.getParcelable(SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER);

                    if (movieListLayoutManagerState != null) {
                        mMovieListLayoutManager.onRestoreInstanceState(movieListLayoutManagerState);
                    }
                });
    }

    protected boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable movieListLayoutManagerState = mMovieListLayoutManager.onSaveInstanceState();

        outState.putParcelable(SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER, movieListLayoutManagerState);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View v, Movie m) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.EXTRA_MOVIE, m);

        startActivity(intent);
    }

    @Optional
    @OnItemSelected(R.id.spSortOrder)
    public void onItemSelected(Spinner spinner, int position) {
        int value = SORT_ORDER_VALUES[position];

        switch (value) {
            case SORT_ORDER_POPULAR:
            case SORT_TOP_RATED:
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
