package com.mpontus.popularmoviesapp.ui.MovieList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.ui.MovieListFragment.MovieListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MovieListActivity extends DaggerAppCompatActivity {

    @BindView(R.id.pager)
    ViewPager mPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_list);
        ButterKnife.bind(this);

        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return new MovieListFragment();
            }

            @Override
            public int getCount() {
                return 1;
            }
        });

    }
}
