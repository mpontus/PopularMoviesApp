package com.mpontus.popularmoviesapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements MovieListAdapter.OnClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    public static final int SORT_ORDER_POPULAR = 1;
    public static final int SORT_ORDER_TOP_RATED = 2;

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
    @BindView(R.id.spSortOrder)
    Spinner mSortOrderView;

    /**
     * Recycler view for movie listing
     */
    @BindView(R.id.rvMovies)
    RecyclerView mMovieListView;

    /**
     * Loading indicator
     */
    @BindView(R.id.progressBar)
    ProgressBar mLoadingIndicator;

    /**
     * Views to be visible when the phone is disconnected from network
     */
    @BindViews({ R.id.tvNoConnectionHeadline, R.id.tvNoConnectionSubheading })
    List<View> mViewsOffline;

    /**
     * Views to be visible while fetching the data
     */
    @BindViews({ R.id.progressBar, R.id.spSortOrder })
    List<View> mViewsFetching;

    /**
     * Views to be visible after the response is loaded
     */
    @BindViews({ R.id.spSortOrder, R.id.rvMovies})
    List<View> mViewsLoaded;

    /**
     * Saved recycler view position
     */
    private Bundle mSavedInstanceState;

    /**
     * Recycler view adapter
     */
    private MovieListAdapter mMovieListAdapter = new MovieListAdapter(this);

    /**
     * Current sort order selection
     */
    int mSortOrder;

    /**
     * Current network status
     */
    boolean mIsOnline;

    /**
     * Flag to signify that we are waiting for network connetion to fetch movies
     */
    boolean mWaitingForNetwork = false;

    /**
     * Layout manager extracted in order to save recycler view position
     */
    private RecyclerView.LayoutManager mMovieListLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((PopularMoviesApplication) getApplication()).getAppComponent()
                .inject(this);

        // Store saved instance state to be used after populating the movie list adapter
        mSavedInstanceState = savedInstanceState;

        // Synchronize mSortOrder with the value in shared preferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        mSortOrder = preferences.getInt(getString(R.string.pref_sort_order_key), SORT_ORDER_POPULAR);

        preferences.registerOnSharedPreferenceChangeListener(this);

        // Synchronize mIsOnline with current connectivity status
        mIsOnline = isOnline();

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mIsOnline = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

                // Retry fetching the movies when connection is reestablished
                if (mIsOnline && mWaitingForNetwork) {
                    fetchMovies();
                }
            }
        }, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        // Initialize movie sort order selector
        // When using array adapter I have to seve the localized strings in preferences, or
        // implement mapping from adapter position to domain value in places distant from this piece
        // of code.
        // This is why I felt the need to implement custom adapter which allow me to operate with
        // domain values and specify the details of mapping them to localized strings.
        SortOrderAdapter<Integer> sortOrderAdapter = new SortOrderAdapter<Integer>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new Integer[]{ SORT_ORDER_POPULAR, SORT_ORDER_TOP_RATED }
                ) {
                    @Override
                    String getLabel(int position) {
                        switch (getItem(position)) {
                            case SORT_ORDER_POPULAR:
                                return getString(R.string.sort_order_popular);

                            case SORT_ORDER_TOP_RATED:
                                return getString(R.string.sort_order_top_rated);

                            default:
                                throw new RuntimeException("Invalid selection");
                        }
                    }
                };

        mSortOrderView.setAdapter(sortOrderAdapter);
        mSortOrderView.setSelection(sortOrderAdapter.getPosition(mSortOrder));

        // Initialize recycler view
        mMovieListLayoutManager = new GridLayoutManager(this, SPAN_COUNT);

        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListView.setLayoutManager(mMovieListLayoutManager);

        fetchMovies();
    }

    /**
     * Save the position of the recycler view
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Parcelable movieListLayoutManagerState = mMovieListLayoutManager.onSaveInstanceState();

        outState.putParcelable(SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER, movieListLayoutManagerState);

        super.onSaveInstanceState(outState);
    }

    /**
     * Open detail activity when the movie is clicked
     */
    @Override
    public void onClick(View v, Movie m) {
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra(DetailActivity.EXTRA_MOVIE, m);

        startActivity(intent);
    }

    /**
     * Update sort order preference when user uses the spinner
     */
    @OnItemSelected(R.id.spSortOrder)
    public void onItemSelected(Spinner spinner, int position) {
        int value = (Integer) mSortOrderView.getItemAtPosition(position);

        switch (value) {
            case SORT_ORDER_POPULAR:
            case SORT_ORDER_TOP_RATED:
                PreferenceManager.getDefaultSharedPreferences(this)
                        .edit()
                        .putInt(getString(R.string.pref_sort_order_key), value)
                        .apply();

                return;

            default:
                throw new RuntimeException("Unexpected option: " + value);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (!key.equals(getString(R.string.pref_sort_order_key))) {
            return;
        }

        mSortOrder = sharedPreferences.getInt(getString(R.string.pref_sort_order_key), SORT_ORDER_POPULAR);

        // Fetch movies when the preference changes
        fetchMovies();
    }

    /**
     * Fetch the movies and populate the recycler view
     */
    protected void fetchMovies() {
        if (!mIsOnline) {
            ButterKnife.apply(mViewsFetching, setVisibility(View.GONE));
            ButterKnife.apply(mViewsLoaded, setVisibility(View.GONE));
            ButterKnife.apply(mViewsOffline, setVisibility(View.VISIBLE));

            mWaitingForNetwork = true;

            return;
        }

        mWaitingForNetwork = false;

        ButterKnife.apply(mViewsOffline, setVisibility(View.GONE));
        ButterKnife.apply(mViewsLoaded, setVisibility(View.GONE));
        ButterKnife.apply(mViewsFetching, setVisibility(View.VISIBLE));

        int sortOrder = PreferenceManager.getDefaultSharedPreferences(this)
                .getInt(getString(R.string.pref_sort_order_key), SORT_ORDER_POPULAR);

        Observable<MovieListResponse> movieListResponseObservable =
                sortOrder == SORT_ORDER_POPULAR
                        ? tmdbService.getPopularMovies()
                        : tmdbService.getTopRatedMovies();

        movieListResponseObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Toast.makeText(this, R.string.fetching_error_message, Toast.LENGTH_SHORT)
                            .show();
                })
                .onErrorResumeNext(Observable.empty())
                .subscribe(
                        response -> {
                            mMovieListAdapter.setMovies(response.results);

                            // Restore recycler view position
                            if (mSavedInstanceState != null) {
                                Parcelable movieListLayoutManagerState = mSavedInstanceState
                                        .getParcelable(SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER);

                                if (movieListLayoutManagerState != null) {
                                    mMovieListLayoutManager.onRestoreInstanceState(
                                            movieListLayoutManagerState
                                    );
                                }

                                mSavedInstanceState = null;
                            }
                        },
                        error -> {
                            Toast.makeText(
                                    this,
                                    R.string.fetching_error_message,
                                    Toast.LENGTH_SHORT
                            ).show();
                        },
                        () -> {
                            ButterKnife.apply(mViewsFetching, setVisibility(View.GONE));
                            ButterKnife.apply(mViewsLoaded, setVisibility(View.VISIBLE));
                        }
                );

    }

    /**
     * Return network status
     */
    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            return false;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Create ButterKnife action which sets View visibility to the given value
     */
    private ButterKnife.Action<View> setVisibility(int value) {
        return (view, index) -> view.setVisibility(value);
    }

    /**
     * Abstract adapter for sort order spinner which allows to override the label rendering method
     */
    abstract class SortOrderAdapter<T> extends BaseAdapter {

        private final LayoutInflater mInflater;
        private final int mResource;
        private final int mTextViewId;
        private final List<T> mOptions;

        SortOrderAdapter(Context context, @LayoutRes int resource, T[] options) {
            this(context, resource, 0, options);
        }

        SortOrderAdapter(Context context, @LayoutRes int resource, @IdRes int textViewId, T[] options) {
            mInflater = LayoutInflater.from(context);
            mResource = resource;
            mTextViewId = textViewId;
            mOptions = Arrays.asList(options);
        }

        @Override
        public int getCount() {
            return mOptions.size();
        }

        @Override
        public T getItem(int position) {
            return mOptions.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String label = this.getLabel(position);
            final View view;
            final TextView text;

            if (convertView == null) {
                view = mInflater.inflate(mResource, parent, false);
            } else {
                view = convertView;
            }

            try {
                if (mTextViewId == 0) {
                    text = (TextView) view;
                } else {
                    text = view.findViewById(mTextViewId);

                    if (text == null) {
                        throw new IllegalStateException("You must supply id of the text view");
                    }
                }
            } catch (ClassCastException e) {
                throw new IllegalStateException("You must supply text view resource id");
            }

            text.setText(label);

            return view;
        }

        int getPosition(T value) {
            return mOptions.indexOf(value);
        }

        abstract String getLabel(int position);
    }
}
