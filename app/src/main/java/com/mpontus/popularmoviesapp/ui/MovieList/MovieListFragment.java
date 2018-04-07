package com.mpontus.popularmoviesapp.ui.MovieList;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

@FragmentScoped
public class MovieListFragment extends DaggerFragment {
    public static final String ARG_MOVIE_SOURCE = "MOVIE_SOURCE";

    @Inject
    MovieListPresenter mPresenter;

    /**
     * Key for the saved state bundle for saving recycler view position
     */
    public static final String SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER = "MOVIE_LIST_LAYOUT_MANAGER";
    /**
     * Recycler view adapter
     */
    @Inject
    MovieListAdapter mMovieListAdapter;

    /**
     * Recycler view layout manager
     */
    @Inject
    RecyclerView.LayoutManager mMovieListLayoutManager;

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
    @BindViews({R.id.tvNoConnectionHeadline, R.id.tvNoConnectionSubheading})
    List<View> mViewsOffline;
    /**
     * Views to be visible while fetching the data
     */
    @BindViews({R.id.progressBar})
    List<View> mViewsFetching;
    /**
     * Views to be visible after the response is loaded
     */
    @BindViews({R.id.rvMovies})
    List<View> mViewsLoaded;

    /**
     * Create new fragment instance for the given movie source
     */
    public static MovieListFragment newInstance(MovieSourceType source) {
        MovieListFragment fragment = new MovieListFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_MOVIE_SOURCE, source.getValue());
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Dagger requires an empty public constructor
     */
    @Inject
    public MovieListFragment() {
    }

    /**
     * Return movie source associated with the fragment
     */
    public MovieSourceType getMovieSource() {
        Bundle args = getArguments();

        if (args == null) {
            return null;
        }

        int value = args.getInt(MovieListFragment.ARG_MOVIE_SOURCE, -1);

        if (value == -1) {
            return null;
        }

        return MovieSourceType.fromValue(value);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

        mPresenter.attach();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_list_fragment, container, false);

        ButterKnife.bind(this, view);

        mMovieListView.setAdapter(mMovieListAdapter);
        mMovieListView.setLayoutManager(mMovieListLayoutManager);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mPresenter.detach();
    }

    public void setMovieCount(int count) {
        mMovieListAdapter.setItemCount(count);
    }

    public void showOffline() {
        ButterKnife.apply(mViewsOffline, setVisibility(View.VISIBLE));
        ButterKnife.apply(mViewsFetching, setVisibility(View.GONE));
        ButterKnife.apply(mViewsLoaded, setVisibility(View.GONE));
    }

    public void showLoading() {
        ButterKnife.apply(mViewsOffline, setVisibility(View.GONE));
        ButterKnife.apply(mViewsFetching, setVisibility(View.VISIBLE));
        ButterKnife.apply(mViewsLoaded, setVisibility(View.GONE));

    }

    public void showMovies() {
        ButterKnife.apply(mViewsOffline, setVisibility(View.GONE));
        ButterKnife.apply(mViewsFetching, setVisibility(View.GONE));
        ButterKnife.apply(mViewsLoaded, setVisibility(View.VISIBLE));

    }

    /**
     * Save the position of the recycler view
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        Parcelable movieListLayoutManagerState = mMovieListLayoutManager.onSaveInstanceState();

        outState.putParcelable(SAVED_STATE_MOVIE_LIST_LAYOUT_MANAGER, movieListLayoutManagerState);

        super.onSaveInstanceState(outState);
    }

    /**
     * Create ButterKnife action which sets View visibility to the given value
     */
    private ButterKnife.Action<View> setVisibility(int value) {
        return (view, index) -> view.setVisibility(value);
    }
}
