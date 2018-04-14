package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mpontus.popularmoviesapp.R;

import javax.inject.Inject;


public class TrailerListAdapter extends RecyclerView.Adapter<TrailerViewHolder> {
    private int mCount = 0;

    private MovieDetailsContract.TrailerItemPresenterFactory mPresenterFactory;

    @Inject
    TrailerListAdapter(MovieDetailsContract.TrailerItemPresenterFactory presenterFactory) {
        mPresenterFactory = presenterFactory;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TrailerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.attachPresenter(mPresenterFactory.createTrailerItemPresenter(holder, position));
    }

    @Override
    public int getItemCount() {
        return mCount;
    }

    public void setItemCount(int count) {
        mCount = count;

        notifyDataSetChanged();
    }

}
