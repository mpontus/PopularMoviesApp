package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.mpontus.popularmoviesapp.R;

import javax.inject.Inject;


public class ReviewListAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private int mCount = 0;

    private MovieDetailsContract.ReviewItemPresenterFactory mPresenterFactory;

    @Inject
    ReviewListAdapter(MovieDetailsContract.ReviewItemPresenterFactory presenterFactory) {
        mPresenterFactory = presenterFactory;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.attachPresenter(mPresenterFactory.createReviewItemPresenter(holder, position));
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
