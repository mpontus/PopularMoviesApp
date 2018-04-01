package com.mpontus.popularmoviesapp.ui.utils;


import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ArrayPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] mPages;
    private String[] mTitles;

    public ArrayPagerAdapter(FragmentManager fragmentManager, Fragment[] pages, String[] titles) {
        super(fragmentManager);

        mPages = pages;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mPages[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }

    @Override
    public int getCount() {
        return mPages.length;
    }
}
