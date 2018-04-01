package com.mpontus.popularmoviesapp.ui.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ArrayPagerAdapter extends FragmentPagerAdapter {

    Fragment[] mPages;

    public ArrayPagerAdapter(FragmentManager fragmentManager, Fragment[] pages) {
        super(fragmentManager);

        mPages = pages;
    }

    @Override
    public Fragment getItem(int position) {
        return mPages[position];
    }

    @Override
    public int getCount() {
        return mPages.length;
    }
}
