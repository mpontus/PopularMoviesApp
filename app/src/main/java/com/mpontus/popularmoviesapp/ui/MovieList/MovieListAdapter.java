package com.mpontus.popularmoviesapp.ui.MovieList;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mpontus.popularmoviesapp.R;

import javax.inject.Inject;

public class MovieListAdapter extends Adapter<MovieListViewHolder> {
    private final MovieListPresenter mPresenter;

    private int mItemCount = 0;

    @Inject
    MovieListAdapter(MovieListPresenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieListViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        holder.attachPresenter(mPresenter.createItemPresenter(holder, position));
    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void setItemCount(int count) {
        mItemCount = count;

        notifyDataSetChanged();
    }

}
