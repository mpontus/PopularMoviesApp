package com.mpontus.popularmoviesapp.ui.MovieListFragment;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mpontus.popularmoviesapp.R;
import com.mpontus.popularmoviesapp.di.FragmentScoped;
import com.mpontus.popularmoviesapp.ui.MovieListItem.MovieListItemViewHolder;

import javax.inject.Inject;

@FragmentScoped
public class MovieListAdapter extends Adapter<MovieListItemViewHolder> {
    private final MovieListPresenter mPresenter;

    private int mItemCount = 0;

    @Inject
    MovieListAdapter(MovieListPresenter presenter) {
        mPresenter = presenter;
    }

    @NonNull
    @Override
    public MovieListItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieListItemViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListItemViewHolder holder, int position) {
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
