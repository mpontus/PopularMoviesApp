package com.mpontus.popularmoviesapp.ui.MovieList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.di.ActivityScoped;
import com.mpontus.popularmoviesapp.domain.MovieSourceType;
import com.mpontus.popularmoviesapp.ui.utils.ArrayPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

@ActivityScoped
public class MovieListActivity extends DaggerAppCompatActivity {

    @BindView(R.id.pager)
    ViewPager mPager;

    @BindView(R.id.tabs)
    TabLayout mTabs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mPager.setAdapter(new ArrayPagerAdapter(getSupportFragmentManager(),
                new Fragment[]{MovieListFragment.newInstance(MovieSourceType.POPULAR),
                        MovieListFragment.newInstance(MovieSourceType.TOP_RATED),
                        MovieListFragment.newInstance(MovieSourceType.FAVORITE)},
                new String[]{getString(R.string.movie_source_popular),
                        getString(R.string.movie_source_top_rated),
                        getString(R.string.movie_source_favorite)}));

        mTabs.setupWithViewPager(mPager);
    }
}
