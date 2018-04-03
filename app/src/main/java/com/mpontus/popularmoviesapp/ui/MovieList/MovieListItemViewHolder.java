package com.mpontus.popularmoviesapp.ui.MovieList;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.mpontus.popularmoviesapp.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MovieListItemViewHolder extends RecyclerView.ViewHolder implements MovieListItemContract.View {
    private MovieListItemContract.Presenter mPresenter;

    @BindView(R.id.ivPoster)
    ImageView mPosterView;

    MovieListItemViewHolder(View view) {
        super(view);

        ButterKnife.bind(this, itemView);
    }

    void attachPresenter(MovieListItemContract.Presenter presenter) {
        if (mPresenter != null) {
            mPresenter.detach();
        }

        mPresenter = presenter;

        mPresenter.attach();
    }

    public void setPoster(Uri posterUrl) {
        Picasso.with(itemView.getContext())
                .load(posterUrl)
                .into(mPosterView);
    }

    @OnClick(R.id.ivPoster)
    public void onClick(View view) {
        mPresenter.onClick();
    }
}
