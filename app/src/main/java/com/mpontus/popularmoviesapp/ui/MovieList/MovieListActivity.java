package com.mpontus.popularmoviesapp.ui.MovieList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;
import com.mpontus.popularmoviesapp.tmdb.Movie;
import com.mpontus.popularmoviesapp.ui.MovieDetails.MovieDetailsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class MovieListActivity extends DaggerAppCompatActivity {

    private static final MovieSourceType[] pageSourceType = new MovieSourceType[]{
            MovieSourceType.POPULAR,
            MovieSourceType.TOP_RATED,
            MovieSourceType.FAVORITE
    };

    private static final int[] pageTitleResource = new int[]{
            R.string.movie_source_popular,
            R.string.movie_source_top_rated,
            R.string.movie_source_favorite
    };

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MovieListFragment.newInstance(pageSourceType[position]);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return getString(pageTitleResource[position]);
            }

            @Override
            public int getCount() {
                return pageSourceType.length;
            }
        });

        mTabs.setupWithViewPager(mPager);
    }

    public void navigateToDetailActivity(Movie movie, View mBackdropView) {
        Context context = this;
        Intent intent = new Intent(context, MovieDetailsActivity.class);

        intent.putExtra(MovieDetailsActivity.EXTRA_MOVIE, movie);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, mBackdropView, "backdrop");

        context.startActivity(intent, options.toBundle());
    }
}
