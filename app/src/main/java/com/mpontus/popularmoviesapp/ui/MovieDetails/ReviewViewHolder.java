package com.mpontus.popularmoviesapp.ui.MovieDetails;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mpontus.popularmoviesapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewViewHolder extends RecyclerView.ViewHolder implements MovieDetailsContract.ReviewItemView {

    @BindView(R.id.tvReviewContent)
    TextView mReviewContentView;

    private MovieDetailsContract.ReviewItemPresenter mPresenter = null;

    ReviewViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, view);
    }

    @Override
    public void attachPresenter(MovieDetailsContract.ReviewItemPresenter presenter) {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        mPresenter = presenter;

        presenter.attach();
    }

    @Override
    public void setContent(String text) {
        mReviewContentView.setText(text);
    }
}
